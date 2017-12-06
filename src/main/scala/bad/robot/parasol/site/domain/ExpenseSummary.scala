package bad.robot.parasol.site.domain

import bad.robot.DateRange

case class ExpenseSummary(assignment: String, period: Either[String, DateRange], amount: String, status: String, id: String)