package bad.robot.parasol

import java.io.{File, FileNotFoundException}

import bad.robot.parasol.site.ExpenseSummaryPredicates._
import bad.robot.parasol.site.domain.April2016
import bad.robot.parasol.site.page.{ExpenseClaimPage, LandingPage}
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}

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
    .expensesAndCosts()
      .reviewExistingCostsAndExpenses()
        .selectFinancialYear(year)
        .getExpenseSummaries(Checked)
          .foreach(download)


  private def download(claim: ExpenseClaimPage) = {
    claim
      .view()
        .download
        .receipts
          .view
          .download
          .close
        .back
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
