package bad.robot.parasol.site.page

import bad.robot.parasol.site.domain.FinancialYear
import bad.robot.webdriver.Locator._
import org.openqa.selenium.By

case class WelcomePage(page: LandingPage) {

  assume(page.driver.getTitle.contains("MyParasol - Welcome"))

  implicit val driver = page.driver

  private val expensesAndCostsMenu = By.id("ctl00_ctl00_menuBlock_menuControl_headerExpenses")
  private val reviewExpensesSubMenu = By.linkText("Review Existing Costs & Expenses")

  def expensesAndCosts: Menu = {
    driver.findElement(expensesAndCostsMenu).click()
    Menu(this)
  }

  case class Menu(page: WelcomePage) {
    def reviewExistingCostsAndExpenses(year: FinancialYear): AllClaimsPage = {
      reviewExpensesSubMenu.waitForElement.click()
      AllClaimsPage(driver, year)
    }
  }

}
