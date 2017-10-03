package bad.robot.parasol

import java.io.{File, FileNotFoundException}

import bad.robot.parasol.site.LandingPage
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}

object Main extends App {

  val driver = init()
  val landingPage = LandingPage(driver)
  val credentials = {
    val user = sys.props.get("user").getOrElse(throw new Exception("no username found, set with -Duser=xxx"))
    val password = sys.props.get("password").getOrElse(throw new Exception("no password found, set with -Dpassword=xxx"))
    (user, password)
  }

  landingPage
    .open
    .login(credentials._1, credentials._2)


  def init() = {
    setDriver()
    new ChromeDriver(new ChromeOptions())
  }

  private def setDriver() = {
    val os: Option[String] = sys.props.get("os.name")
    println(os)
    val executable = s"src/main/resources/chromedriver_win32_2.31/chromedriver.exe"

    if (!new File(executable).exists())
      throw new FileNotFoundException(s"Can't find driver based on the working directory: $executable")

    System.setProperty("webdriver.chrome.driver", executable)
  }
}
