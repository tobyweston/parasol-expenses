package bad.robot.parasol.site

import org.openqa.selenium.{By, WebElement}

class GetExpenses(page: ReviewExistingExpenses) extends Extraction {
  import scala.collection.JavaConverters._

  def getExpenseSummaries: GetExpenses = {
    val expenses = getExpenseRows.filter(expenseLine).map(toExpenseSummary)
    print(expenses.mkString("\n"))
    this
  }

  private def getExpenseRows: List[WebElement] = {
    val table = page.driver.findElement(By.id("ctl00_ctl00_mainContent_MainContent_gridListing"))
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

  def end = page
}
