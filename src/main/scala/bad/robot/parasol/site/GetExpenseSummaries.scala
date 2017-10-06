package bad.robot.parasol.site

import bad.robot.parasol.site.domain.ExpenseSummary
import bad.robot.parasol.site.page.AllClaimsPage
import org.openqa.selenium.{By, WebElement}

import scala.collection.JavaConverters._

object GetExpenseSummaries {
  val Checked: ExpenseSummary => Boolean = _.status == "Checked"
  val ResponseRequired: ExpenseSummary => Boolean = _.status == "Outstanding query"
  val All: ExpenseSummary => Boolean = _.status == true
}

class GetExpenseSummaries(page: AllClaimsPage) extends Extraction {

  def getExpenseSummaries(filter: ExpenseSummary => Boolean): GetExpenseSummaries = {
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
