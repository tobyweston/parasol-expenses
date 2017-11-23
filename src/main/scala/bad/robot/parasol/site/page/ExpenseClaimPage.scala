package bad.robot.parasol.site.page

import bad.robot.parasol.site.domain.ExpenseSummary
import org.openqa.selenium.By

case class ExpenseClaimPage(parent: AllClaimsPage, summary: ExpenseSummary) {

  def view() = {
    driver.findElement(By.id(summary.id)).click()
    this
  }

  def extract = {
    val title = driver.findElement(By.id("ctl00_ctl00_pageTitle_pageTitle_heading")).getText
    println(s"title = $title")
    this
  }

  def receipts = {
    UploadedReceiptsPage(this, None)
  }

  def back = {
    driver.navigate().back()
  }

  def driver = parent.driver

  def end = parent

}


