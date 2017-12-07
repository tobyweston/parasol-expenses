package bad.robot.parasol.site.domain

import argonaut.CodecJson

object Claim {
  implicit val codec = CodecJson.derive[Claim]
}

case class Claim(summary: ExpenseSummary, expenses: List[Expenses])
