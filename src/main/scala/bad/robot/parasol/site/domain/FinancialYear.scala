package bad.robot.parasol.site.domain

trait FinancialYear {
  val text: String
}

case object April2014 extends FinancialYear { override val text = "April 2014 - March 2015" }
case object April2015 extends FinancialYear { override val text = "April 2015 - March 2016" }
case object April2016 extends FinancialYear { override val text = "April 2016 - March 2017" }
case object April2017 extends FinancialYear { override val text = "April 2017 - March 2018" }
case object April2018 extends FinancialYear { override val text = "April 2018 - March 2019" }
