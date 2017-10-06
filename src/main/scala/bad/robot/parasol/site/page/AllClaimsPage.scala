package bad.robot.parasol.site.page

import bad.robot.parasol.site.GetExpenses
import bad.robot.parasol.site.domain.FinancialYear
import org.openqa.selenium.{By, WebDriver}
import bad.robot.webdriver._

case class AllClaimsPage(driver: WebDriver) {

  def selectFinancialYear(year: FinancialYear) = {

    def updatePageWithFinancialYear = driver.findElement(By.id("ctl00_ctl00_mainContent_MainContent_buttonFilter")).click()
    def updatePagination = select(By.id("ctl00_ctl00_mainContent_MainContent_gridFilter_ItemList"), "50", driver)

    select(By.id("ctl00_ctl00_mainContent_MainContent_ddlFinancialYear"), year.text, driver)
    updatePageWithFinancialYear
    updatePagination

    new GetExpenses(this)
  }

}
