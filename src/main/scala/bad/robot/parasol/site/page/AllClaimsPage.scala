package bad.robot.parasol.site.page

import bad.robot.parasol.site.domain.{ExpenseSummary, FinancialYear}
import bad.robot.webdriver._
import org.openqa.selenium.{By, WebDriver, WebElement}

import scala.collection.JavaConverters._

case class AllClaimsPage(driver: WebDriver) {

  def selectFinancialYear(year: FinancialYear) = {

    def updatePageWithFinancialYear = driver.findElement(By.id("ctl00_ctl00_mainContent_MainContent_buttonFilter")).click()

    select(By.id("ctl00_ctl00_mainContent_MainContent_ddlFinancialYear"), year.text, driver)
    updatePageWithFinancialYear
    showNumberOfClaims(50)

    this
  }

  def showNumberOfClaims(number: Int) = {
    assert(number == 10 || number == 20 || number == 50)
    select(By.id("ctl00_ctl00_mainContent_MainContent_gridFilter_ItemList"), number.toString, driver)
  }

  def getExpenseSummaries(summaryType: ExpenseSummary => Boolean): List[ExpenseClaimPage] = {
    getExpenseRows
      .filter(expenseLine)
      .map(toExpenseSummary)
      .filter(summaryType)
      .map(ExpenseClaimPage(this, _))
  }

  private def getExpenseRows: List[WebElement] = {
    val table = driver.findElement(By.id("ctl00_ctl00_mainContent_MainContent_gridListing"))
    val subTable = table.findElement(By.tagName("table"))
    val rows = subTable.findElements(By.tagName("tr")).asScala.toList
    rows
  }

  private val expenseLine: WebElement => Boolean = row => row.getAttribute("id").contains("ctl00_ctl00_mainContent_MainContent_gridListing_row")

  private val toExpenseSummary: WebElement => ExpenseSummary = row => {
    val cells = row.findElements(By.tagName("td")).asScala.toList
    val assignment = cells(0).getText
    val period     = cells(1).getText
    val amount     = cells(2).getText
    val status     = cells(3).getText
    val id         = cells(4).getAttribute("id")
    ExpenseSummary(assignment, period, amount, status, id)
  }

}
