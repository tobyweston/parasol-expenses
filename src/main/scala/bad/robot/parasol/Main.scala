package bad.robot.parasol

import java.io.{File, FileNotFoundException}
import java.nio.file.{Files, Path}

import argonaut.Parse
import bad.robot.parasol.DownloadLocation.save
import bad.robot.parasol.site.ExpenseSummaryPredicates._
import bad.robot.parasol.site.domain.{April2016, Claim}
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
  val year = April2016

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
      case Some("Mac OS X")                                    => "src/main/resources/chromedriver_mac64_2.31/chromedriver"
      case Some(other) if other.toLowerCase contains "windows" => "src/main/resources/chromedriver_win32_2.31/chromedriver.exe"
      case None                                                => ???
    }
    
    if (!new File(executable).exists())
      throw new FileNotFoundException(s"Can't find driver based on the working directory: $executable")

    System.setProperty("webdriver.chrome.driver", executable)
  }
}

object GatherExpenses extends App {
  private val expenses: List[Path] = DownloadLocation.findExpenses

  val load: Path => Either[String, Claim] = path => {
    val asString = Source.fromFile(path.toFile).getLines().mkString("")
    Parse.decodeEither[Claim](asString)
  }
  
  println(s"Found ${expenses.size} weeks:")
  expenses.map(load).foreach {
    case Left(error)  => println("Error: " + error)
    case Right(claim) => println(claim.summary.period.right.get + " " + claim.summary.amount)
  }
}