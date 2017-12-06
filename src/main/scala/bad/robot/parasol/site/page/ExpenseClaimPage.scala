package bad.robot.parasol.site.page

import java.io.File
import java.nio.charset.StandardCharsets._
import java.nio.file.Files

import bad.robot._
import bad.robot.parasol.site.domain.{Expense, Expenses, ExpenseSummary}
import bad.robot.parasol.site.page.ExpenseCategories.{Category, Mileage, TravelAndCarHire, all}
import bad.robot.webdriver.{waitUntilVisible, _}
import org.openqa.selenium.{By, WebElement}

import scala.collection.JavaConverters._

case class ExpenseClaimPage(parent: AllClaimsPage, summary: ExpenseSummary) {

  def view() = {
    waitUntilVisible(By.id(summary.id))(driver).click()
    this
  }

  def download = {
    val title = driver.findElement(By.id("ctl00_ctl00_pageTitle_pageTitle_heading")).getText
    println(s"\n$title")

    val claimsRegion = driver.findElement(By.id("ctl00_ctl00_mainContent_MainContent_claimList_ExpensesContentPanel"))
    val expenses = all.map(category => {
      extractExpenses(category, claimsRegion.findElement(By.id(category.id)))
    })

    save(expenses.filter(_.expenses.nonEmpty))

    this
  }

  def receipts = {
    UploadedReceiptsPage(this, summary.period, None)
  }

  def back = {
    def tryAndForceARefreshAsItsFlaky(): Unit = {
      waitForElement(By.id("ctl00_ctl00_mainContent_MainContent_gridListing_footer"))(driver)
      parent.showNumberOfClaims(50)
      parent.showNumberOfClaims(20)
      parent.showNumberOfClaims(50)
    }

    driver.navigate().back()
    if (requiresRefresh()) {
      driver.navigate.refresh()
      tryAndForceARefreshAsItsFlaky()
    }
  }

  private def requiresRefresh() = {
    val elements = driver.findElements(By.tagName("h1")).asScala.toList
    if (elements.exists(element => element.getText.equalsIgnoreCase("Confirm Form Resubmission"))) true
    else false
  }

  def driver = parent.driver

  def end = parent

  private def extractExpenses(category: Category, claim: WebElement) = {
    val description = claim.findElement(By.id(category.description)).getText
    val total = claim.findElements(By.cssSelector(".col-lg-2.col-md-2.col-sm-3.col-xs-6")).asScala.head.getText
    val items = claim.findElements(By.cssSelector(".col-lg-2.col-md-2.col-sm-3.col-xs-6")).asScala(1).getText
    val expandLink = claim.findElement(By.tagName("a"))

    val details = Expenses(summary, description, total, items, Nil)

    val expenses = if (details.items > 0) {
      expandLink.click()
      val table = waitUntilVisible(By.id(category.details))(driver)
      val rows = table.findElements(By.tagName("tr")).asScala.toList
      rows
        .drop(1)
        .flatMap(toExpense(category, _))
    } else {
      Nil
    }

    details.copy(expenses = expenses)
  }

  private val toExpense: ((Category, WebElement)) => Option[Expense] = {
    case (Mileage, _)            => None

    case (TravelAndCarHire, row) =>
      val cells = row.findElements(By.tagName("td")).asScala.toList
      val description = Some(cells.head.getText)
      // val amountPerDay = cells(1).getText
      val dates = cells(2).getText
      val total = cells(3).getText
      Some(Expense(dates, total, description))

    case (_, row)                =>
      val cells = row.findElements(By.tagName("td")).asScala.toList
      val date = cells.head.getText
      val amount = cells(1).getText
      Some(Expense(date, amount))
  }

  def save(expenses: List[Expenses]) = {
    import argonaut.Argonaut._

    val downloadLocation = new File(System.getProperty("user.home")) / "Downloads"
    val folder = downloadLocation / summary.period.map(_.toString()).getOrElse(summary.period.left.get)

    if (!folder.exists())
      folder.mkdirs()

    val json = expenses.jencode.spaces2
    Files.write((folder / "expenses.json").toPath, json.getBytes(UTF_8))
  }

}




