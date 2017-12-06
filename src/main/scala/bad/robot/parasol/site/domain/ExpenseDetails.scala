package bad.robot.parasol.site.domain

object ExpenseDetails {
  def apply(description: String, total: String, items: String, expenses: List[Expense]) = {
    new ExpenseDetails(
      description,
      """\d*\.\d{2}""".r.findFirstIn(total).map(_.toDouble).getOrElse(0),
      """\d*""".r.findFirstIn(items).map(_.toInt).getOrElse(0),
      expenses
    )
  }
}

case class ExpenseDetails(description: String, total: Double, items: Int, expenses: List[Expense])





