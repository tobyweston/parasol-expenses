package bad.robot.parasol.site.domain

import argonaut.CodecJson

object Expenses {

  implicit val codec = CodecJson.derive[Expenses]

  def apply(summary: ExpenseSummary, description: String, total: String, items: String, expenses: List[Expense]) = {
    new Expenses(
      summary,
      description,
      """\d*\.\d{2}""".r.findFirstIn(total).map(_.toDouble).getOrElse(0),
      """\d*""".r.findFirstIn(items).map(_.toInt).getOrElse(0),
      expenses
    )
  }
}

case class Expenses(summary: ExpenseSummary, description: String, total: Double, items: Int, expenses: List[Expense])





