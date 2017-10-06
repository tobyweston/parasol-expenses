package bad.robot.parasol.site

import org.openqa.selenium.{By, WebDriver}
import bad.robot.webdriver._
import bad.robot.webdriver.Locator._
import org.openqa.selenium.support.ui.Select

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

  import scala.collection.JavaConverters._

  def selectFinancialYear(year: FinancialYear) = {
    val select = new Select(driver.findElement(By.id("ctl00_ctl00_mainContent_MainContent_ddlFinancialYear")))
    if (select.getOptions.asScala.exists(_.getText == year.text)) {
      select.selectByVisibleText(year.text)
      driver.findElement(By.id("ctl00_ctl00_mainContent_MainContent_buttonFilter")).click()
    } else throw new NoSuchElementException(s"Can not find the year '${year.text}' in the 'Financial Year' dropdown")
    this
  }
}
