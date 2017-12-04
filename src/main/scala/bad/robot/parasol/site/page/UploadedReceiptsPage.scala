package bad.robot.parasol.site.page

import bad.robot.webdriver.PageObject
import org.openqa.selenium.{By, WebElement}

import scala.collection.JavaConverters._

case class UploadedReceiptsPage(parent: ExpenseClaimPage, page: Option[WebElement]) extends PageObject {

  def view = {
    val viewReceiptsButton = By.id("ctl00_ctl00_mainContent_MainContent_businessExpenseClaim_uploadedReceipts_uploadedReceipts")
    parent.driver.findElement(viewReceiptsButton).click()

    val receiptsModal = By.id("ctl00_ctl00_mainContent_MainContent_businessExpenseClaim_uploadedReceipts_pnlUploadedReceipts_uploadedReceiptsDisplayView")
    UploadedReceiptsPage(parent, Some(parent.driver.findElement(receiptsModal)))
  }

  def download = {
    val heading: WebElement => Boolean = row => row.findElements(By.tagName("th")).size() > 0
    val click: WebElement => Unit = row => row.findElement(By.tagName("a")).click()

    executeOn(page => {
      val table = page.findElement(By.className("uploadedReceiptsTable"))
      val rows = table.findElements(By.tagName("tr")).asScala.toList
      rows.filterNot(heading).foreach(click)
    })
    this
  }

  def close = {
    parent.driver.findElement(By.id("ctl00_ctl00_mainContent_MainContent_businessExpenseClaim_uploadedReceipts_pnlUploadedReceipts_CloseButton")).click()
    parent
  }
}
