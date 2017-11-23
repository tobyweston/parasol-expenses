package bad.robot.webdriver

import org.openqa.selenium.WebElement

trait PageObject {
  protected val page: Option[WebElement]

  def view: PageObject

  def executeOn[A](interaction: WebElement => A): A = {
    page.map(interaction).getOrElse(sys.error("unable to interact with page object, did you call 'view'?"))
  }
}
