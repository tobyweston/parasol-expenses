package bad.robot

import com.google.common.base.{Function => GuavaFunction, Predicate => GuavaPredicate}
import org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable
import org.openqa.selenium.support.ui.{Select, WebDriverWait}
import org.openqa.selenium.{By, WebDriver, WebElement, _}

import scala.collection.JavaConverters

package object webdriver {

  implicit def functionToPredicate(predicate: WebDriver => Boolean): GuavaPredicate[WebDriver] = {
    (driver: WebDriver) => predicate.apply(driver)
  }

  implicit def functionToFunction[A, W >: WebDriver](function: W => A): GuavaFunction[W, A] = {
    (driver: W) => function.apply(driver)
  }


  def select(locator: By, text: String, driver: WebDriver) = {
    import scala.collection.JavaConverters._

    val select = new Select(driver.findElement(locator))
    if (select.getOptions.asScala.exists(_.getText == text)) {
      select.selectByVisibleText(text)
    } else {
      throw new NoSuchElementException(s"Could not find the text '$text' in a dropdown")
    }
  }


  def waitForElement(locator: By)(implicit driver: WebDriver): WebElement = {
    waitForCondition(driver, _.findElement(locator), s"waiting for element '$locator' on page '${driver.getCurrentUrl}'")
  }

  def findElements(locator: By)(implicit driver: WebDriver): List[WebElement] = {
    import JavaConverters._
    driver.findElements(locator).asScala.toList
  }

  def waitForElementToBeInteractable(locator: By)(implicit driver: WebDriver): WebElement = {
    val interactable: (WebDriver) => Boolean = e => {
      val element: WebElement = e.findElement(locator)
      element.isEnabled && element.isDisplayed
    }

    waitForCondition(driver, interactable, s"waiting for element '$locator' on page '${driver.getCurrentUrl}'")
    driver.findElement(locator)
  }

  def waitUntilVisible(locator: By)(implicit driver: WebDriver): WebElement = {
    waitForCondition(driver, _.findElement(locator).isDisplayed, s"waiting for element $locator to be visible")
    driver.findElement(locator)
  }

  def waitUntilClickable(element: WebElement)(implicit driver: WebDriver): WebElement = {
    new WebDriverWait(driver, 5).until(elementToBeClickable(element))
  }

  def waitUntilNotPresent(locator: By)(implicit driver: WebDriver): Unit = {
    waitForCondition(driver, _.findElements(locator).size() == 0, s"waiting for element $locator to not be present")
  }

  private def waitForCondition[A](driver: WebDriver, predicate: WebDriver => A, message: String): A = {
    new WebDriverWait(driver, 5)
      .withMessage(message)
      .ignoring(classOf[NoSuchElementException])
      .ignoring(classOf[ElementNotVisibleException])
      .ignoring(classOf[StaleElementReferenceException])
      .ignoring(classOf[NoAlertPresentException])
      .until(predicate)
  }
}
