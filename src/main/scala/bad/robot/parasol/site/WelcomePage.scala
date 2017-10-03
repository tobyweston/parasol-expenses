package bad.robot.parasol.site

import org.openqa.selenium.{By, WebDriver}
import bad.robot.webdriver._
import bad.robot.webdriver.Locator._

case class WelcomePage(page: LandingPage) {

  assume(page.driver.getTitle.contains("MyParasol - Welcome"))

  implicit val driver = page.driver

  private val expensesAndCostsMenu = By.id("ctl00_ctl00_menuBlock_menuControl_headerExpenses")
  private val reviewExpensesSubMenu = By.linkText("Review Existing Costs & Expenses")

  def expensesAndCosts(): Menu = {
    driver.findElement(expensesAndCostsMenu).click()
    Menu(this)
  }

  case class Menu(page: WelcomePage) {
    def reviewExistingCostsAndExpenses() = {
      reviewExpensesSubMenu.waitForElement.click()
      ReviewExistingExpenses(driver)
    }
  }

}

case class ReviewExistingExpenses(driver: WebDriver) {

}
