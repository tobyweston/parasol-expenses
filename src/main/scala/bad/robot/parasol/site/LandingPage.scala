package bad.robot.parasol.site

import org.openqa.selenium.{By, WebDriver}

case class LandingPage(driver: WebDriver) {

  def open: LandingPage = {
    driver.get("https://myparasol.co.uk")
    this
  }

  def login(user: String, password: String): WelcomePage = {
    driver.findElement(By.id("ctl00_ctl00_mainContent_main_userLogin_UserName")).sendKeys(user)
    driver.findElement(By.id("ctl00_ctl00_mainContent_main_userLogin_Password")).sendKeys(password)
    driver.findElement(By.id("ctl00_ctl00_mainContent_main_userLogin_Button1")).click()
    WelcomePage(this)
  }

}
