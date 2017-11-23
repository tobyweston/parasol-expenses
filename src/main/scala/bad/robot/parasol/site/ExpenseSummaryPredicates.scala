package bad.robot.parasol.site

import bad.robot.parasol.site.domain.ExpenseSummary

object ExpenseSummaryPredicates {
  val Checked: ExpenseSummary => Boolean = _.status == "Checked"
  val ResponseRequired: ExpenseSummary => Boolean = _.status == "Outstanding query"
  val All: ExpenseSummary => Boolean = _.status == true
}
