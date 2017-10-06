package bad.robot.parasol.site

import bad.robot.webdriver.Locator._
import bad.robot.webdriver._
import org.openqa.selenium.{By, WebDriver}

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
    def reviewExistingCostsAndExpenses(): ReviewExistingExpenses = {
      reviewExpensesSubMenu.waitForElement.click()
      ReviewExistingExpenses(driver)
    }
  }

}

case class ReviewExistingExpenses(driver: WebDriver) {

  def selectFinancialYear(year: FinancialYear) = {

    def updatePageWithFinancialYear = driver.findElement(By.id("ctl00_ctl00_mainContent_MainContent_buttonFilter")).click()
    def updatePagination = select(By.id("ctl00_ctl00_mainContent_MainContent_gridFilter_ItemList"), "50", driver)

    select(By.id("ctl00_ctl00_mainContent_MainContent_ddlFinancialYear"), year.text, driver)
    updatePageWithFinancialYear
    updatePagination

    new GetExpenses(this)
  }

}
