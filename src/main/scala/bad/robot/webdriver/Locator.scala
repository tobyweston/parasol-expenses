package bad.robot.webdriver

import org.openqa.selenium.{By, WebDriver, WebElement}
import bad.robot.webdriver

/**
  * implicit version of the `bad.robot.webdriver` helper methods.
  */
object Locator {

  implicit class LocatorOps(locator: By) {

    def waitForElement(implicit driver: WebDriver): WebElement = {
      webdriver.waitForElement(locator)
    }

    // don't know how this could work. By has a findElements method so it clashes
    def findElements(implicit driver: WebDriver): List[WebElement] = {
      webdriver.findElements(locator)
    }

    def waitForElementToBeInteractable(implicit driver: WebDriver): WebElement = {
      webdriver.waitForElementToBeInteractable(locator)
    }

    def waitUntilVisible(implicit driver: WebDriver): WebElement = {
      webdriver.waitUntilVisible(locator)
    }

    def waitUntilNotPresent(implicit driver: WebDriver): Unit = {
      webdriver.waitUntilNotPresent(locator)
    }
  }

}