package bad.robot.parasol.site.domain

import argonaut.CodecJson
import bad.robot.DateRange
import bad.robot.parasol.Amount

object ExpenseSummary {
  implicit val codec = CodecJson.derive[ExpenseSummary]
  
  def apply(assignment: String, period: Either[String, DateRange], amount: String, status: String, id: String) = {
    new ExpenseSummary(assignment, period, Amount.parse(amount), status, id)
  }
}

case class ExpenseSummary(assignment: String, period: Either[String, DateRange], amount: Double, status: String, id: String)