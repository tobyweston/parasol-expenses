package bad.robot.parasol.site.page

import bad.robot._
import bad.robot.webdriver.waitUntilVisible
import bad.robot.parasol.site.domain.ExpenseSummary
import org.openqa.selenium.By
import scala.collection.JavaConverters._
import bad.robot.webdriver._

case class ExpenseClaimPage(parent: AllClaimsPage, summary: ExpenseSummary) {

  def view() = {
    waitUntilVisible(By.id(summary.id))(driver).click()
    this
  }

  def extract = {
    val title = driver.findElement(By.id("ctl00_ctl00_pageTitle_pageTitle_heading")).getText
    // todo grab all the details to categorise the expenses
    println(s"$title")
    this
  }

  def receipts = {
    UploadedReceiptsPage(this, summary.period.toDateRange.left.map(_ => summary.period), None)
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

}


