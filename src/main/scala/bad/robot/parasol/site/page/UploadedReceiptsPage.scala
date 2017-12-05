package bad.robot.parasol.site.page

import java.io.File
import java.util.concurrent.TimeUnit._

import bad.robot._
import bad.robot.webdriver.PageObject
import org.openqa.selenium.{By, WebElement}

import scala.collection.JavaConverters._
import scala.concurrent.duration.Duration

case class UploadedReceiptsPage(parent: ExpenseClaimPage, period: String, page: Option[WebElement]) extends PageObject {

  val downloadLocation = "/Users/toby/Downloads"

  def view = {
    val viewReceiptsButton = By.id("ctl00_ctl00_mainContent_MainContent_businessExpenseClaim_uploadedReceipts_uploadedReceipts")
    parent.driver.findElement(viewReceiptsButton).click()

    val receiptsModal = By.id("ctl00_ctl00_mainContent_MainContent_businessExpenseClaim_uploadedReceipts_pnlUploadedReceipts_uploadedReceiptsDisplayView")
    UploadedReceiptsPage(parent, period, Some(parent.driver.findElement(receiptsModal)))
  }

  def download = {
    val heading: WebElement => Boolean = row => row.findElements(By.tagName("th")).size() > 0
    val link: WebElement => WebElement = row => row.findElement(By.tagName("a"))
    val download: WebElement => WebElement = link => {
      link.click()
      link
    }
    val rename: WebElement => Unit = link => {
      val file = new File(downloadLocation) / link.getText
      val downloading = new File(downloadLocation) / (link.getText + ".crdownload")
      val folder = new File(downloadLocation) / period
      
      if (!folder.exists())
        folder.mkdirs()
      WaitFor.waitFor(file.exists() & !downloading.exists(), Timeout(Duration(10, SECONDS)))
      WaitFor.waitFor(file.renameTo(folder / link.getText), Timeout(Duration(10, SECONDS)))
//      Files.move(file.toPath, folder.toPath, REPLACE_EXISTING)
//      file.renameTo(folder / link.getText)
    }

    executeOn(page => {
      val table = page.findElement(By.className("uploadedReceiptsTable"))
      val rows = table.findElements(By.tagName("tr")).asScala.toList
      rows
        .filterNot(heading)
        .map(link)
        .map(download)
        .map(rename)
    })
    this
  }

  def close = {
    parent.driver.findElement(By.id("ctl00_ctl00_mainContent_MainContent_businessExpenseClaim_uploadedReceipts_pnlUploadedReceipts_CloseButton")).click()
    parent
  }
}
