package bad.robot.parasol.site.page

import bad.robot.parasol.site.domain.{Expense, ExpenseSummary, Expenses}
import bad.robot.parasol.site.page.ExpenseCategories._
import bad.robot.webdriver.{waitUntilVisible, _}
import org.openqa.selenium.{By, WebElement}

import scala.collection.JavaConverters._

case class ExpenseClaimPage(parent: AllClaimsPage, summary: ExpenseSummary, expenses: List[Expenses]) {

  def view() = {
    waitUntilVisible(By.id(summary.id))(driver).click()
    this
  }

  def extract = {
    val title = driver.findElement(By.id("ctl00_ctl00_pageTitle_pageTitle_heading")).getText
    println(s"$title")

    val claimsRegion = driver.findElement(By.id("ctl00_ctl00_mainContent_MainContent_claimList_ExpensesContentPanel"))
    val expenses = all.map(category => {
      extractExpenses(category, claimsRegion.findElement(By.id(category.id)))
    })

    ExpenseClaimPage(parent, summary, expenses.filter(_.items.nonEmpty))
  }

  def receipts = {
    UploadedReceiptsPage(this, summary.period, None)
  }

  def back = {
    def tryAndForceARefreshAsItsFlaky(): Unit = {
      waitForElement(By.id("ctl00_ctl00_mainContent_MainContent_gridListing_footer"))(driver)
      parent.selectFinancialYear
      parent.showNumberOfClaims(50)
    }

    driver.navigate().back()
    if (requiresRefresh()) {
      driver.navigate.refresh()
      tryAndForceARefreshAsItsFlaky()
    }
    
    this.expenses
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
    val numberOfItems = claim.findElements(By.cssSelector(".col-lg-2.col-md-2.col-sm-3.col-xs-6")).asScala(1).getText
    val expandLink = claim.findElement(By.id(category.expander))

    val expenses = Expenses(description, total, numberOfItems, Nil)
    
    val items = category match {
      case Mileage if expenses.nonEmpty => List(Expense(summary.period.right.get.start, expenses.total, Some("Mileage (abbreviated)")))
      case _ if expenses.nonEmpty       => extractItemsFromTable(category, expandLink, expenses)
      case _                            => Nil
    }

    expenses.copy(items = items)
  }

  private def extractItemsFromTable(category: Category, expandLink: WebElement, expenses: Expenses): List[Expense] = {
    expandLink.click()
    val table = waitUntilVisible(By.id(category.details))(driver)
    val rows = table.findElements(By.tagName("tr")).asScala.toList
    rows
      .drop(1)
      .flatMap(scrapeCategoryExpenseItems(category, _))
  }

  private val scrapeCategoryExpenseItems: ((Category, WebElement)) => Option[Expense] = {
    case (Mileage, _)               => None
    
    case (TravelAndCarHire, row)    =>
      val cells = row.findElements(By.tagName("td")).asScala.toList
      val description = Some(cells(0).getText)
      val date = cells(2).getText
      val total = cells(3).getText
      Some(Expense(date, total, description))
    
    case (ElectronicEquipment, row) =>
      val cells = row.findElements(By.tagName("td")).asScala.toList
      val description = Some(cells(0).getText)
      val amount = cells(1).getText
      val date = cells(2).getText
      Some(Expense(date, amount, description))

    case (FoodAndDrink, row)        =>
      val cells = row.findElements(By.tagName("td")).asScala.toList
      val simplifiedView = cells.size == 3
      if (simplifiedView) {
        val date = cells.head.getText
        val amount = cells(1).getText
        val description = cells(2).getText.replaceAll("Receipt required \\(", "").replaceAll("\\)", "")
        Some(Expense(date, amount, Some(description)))
      } else {
        val date = cells.head.getText
        val description = cells(8).getText.replaceAll("Receipts required \\(", "").replaceAll("\\)", "")
        val breakfast = Expense(date, cells(1).getText)
        val lunch = Expense(date, cells(3).getText)
        val dinner = Expense(date, cells(6).getText)
        Some(breakfast.copy(amount = breakfast.amount + lunch.amount + dinner.amount, description = Some(description)))
      }
    
    case (_, row)                   =>
      val cells = row.findElements(By.tagName("td")).asScala.toList
      val date = cells.head.getText
      val amount = cells(1).getText
      Some(Expense(date, amount))
  }

}




