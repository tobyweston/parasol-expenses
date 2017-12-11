package bad.robot.parasol.site.domain

import java.time.LocalDate

import argonaut.CodecJson

object Claim {
  implicit val codec = CodecJson.derive[Claim]
  implicit val ordering = new Ordering[Claim] {
    def compare(first: Claim, second: Claim) = first.startDate.compareTo(second.startDate)
  }
}

case class Claim(summary: ExpenseSummary, expenses: List[Expenses]) {
  def startDate = summary.period.map(_.start).getOrElse(LocalDate.ofEpochDay(0))
}
