package bad.robot.parasol.site.domain

import argonaut.CodecJson
import bad.robot.DateRange
import bad.robot.Codecs._

object ExpenseSummary {
  implicit val codec = CodecJson.derive[ExpenseSummary]
}

case class ExpenseSummary(assignment: String, period: Either[String, DateRange], amount: String, status: String, id: String)