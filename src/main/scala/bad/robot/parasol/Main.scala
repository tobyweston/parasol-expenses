package bad.robot.parasol

import java.io.{File, FileNotFoundException}
import java.nio.file.Path

import argonaut.Parse
import bad.robot.parasol.DownloadLocation.save
import bad.robot.parasol.site.ExpenseSummaryPredicates._
import bad.robot.parasol.site.domain.{April2017, Claim}
import bad.robot.parasol.site.page.{ExpenseClaimPage, LandingPage}
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}

import scala.io.Source

object Main extends App {

  val driver = init()
  val landingPage = LandingPage(driver)
  val credentials = {
    val user = sys.props.get("user").getOrElse(throw new Exception("no username found, set with -Duser=xxx"))
    val password = sys.props.get("password").getOrElse(throw new Exception("no password found, set with -Dpassword=xxx"))
    (user, password)
  }
  val year = April2017

  val expenses = landingPage
    .open
    .login(credentials._1, credentials._2)
    .expensesAndCosts
    .reviewExistingCostsAndExpenses(year)
    .selectFinancialYear
    .getExpenseSummaries(Checked)
    .foreach(download)


  private def download(claim: ExpenseClaimPage) = {
    val expenses = claim
      .view()
      .extract
      .receipts
      .view
      .download
      .close
      .back

    save(Claim(claim.summary, expenses))
  }

  def init() = {
    setDriver()
    new ChromeDriver(new ChromeOptions())
  }

  private def setDriver() = {
    val os = sys.props.get("os.name")

    val executable = os match {
      case Some("Mac OS X")                                    => "src/main/resources/chromedriver_mac64_2.45/chromedriver"
      case Some(other) if other.toLowerCase contains "windows" => "src/main/resources/chromedriver_win32_2.45/chromedriver.exe"
      case None                                                => ???
    }

    if (!new File(executable).exists())
      throw new FileNotFoundException(s"Can't find driver based on the working directory: $executable")

    System.setProperty("webdriver.chrome.driver", executable)
  }
}

object DeleteExpensesJson extends App {
//  DownloadLocation.findExpenses.foreach(_.toFile.delete())  
}

object GatherExpenses extends App {
  private val files: List[Path] = DownloadLocation.findExpenses

  val load: Path => Either[String, Claim] = path => {
    val asString = Source.fromFile(path.toFile).getLines().mkString("")
    Parse.decodeEither[Claim](asString)
  }

  def expenseAmount(amountFrom: Claim => Double): PartialFunction[Either[String, Claim], Double] = {
    case Left(error)  => println("Error: " + error); 0
    case Right(claim) => println(s"${claim.startDate} - ${claim.endDate}   £ ${claim.summary.amount}    (£ ${claim.expenses.flatMap(_.items).map(_.amount).sum} crc)"); amountFrom(claim)
  }
  
  println(s"Found ${files.length} weeks:")
  val expenses = files.map(load).sortBy(_.right.get)
  val total = expenses.map(expenseAmount(_.summary.amount)).sum
  val crc = expenses.map(expenseAmount(_.expenses.flatMap(_.items).map(_.amount).sum)).sum

  println(s"\ntotal:        £ $total  ($crc crc)\n")

  val csv = toCsv(expenses.map(_.right.get))
  println("assignment,status,periodstart,periodend,summary,date,description,amount")
  csv.foreach(row => {
    println(s"${row(0)},${row(1)},${row(2)},${row(3)},${row(4)},${row(5)},${row(6)},${row(7)}")
  })

  
  def toCsv(claims: List[Claim]) = {
    for {
      claim   <- claims
      expense <- claim.expenses
      item    <- expense.items
    } yield {
      val summary = claim.summary
      Array(summary.assignment.trim, summary.status.trim, claim.startDate.toString.trim, claim.endDate.toString.trim, expense.description.trim, item.date.toString.trim, item.description.getOrElse("").replaceAll(",", "").trim, item.amount)
    }
  }
}