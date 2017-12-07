package bad.robot.parasol.site.domain

import argonaut.CodecJson

object Expenses {

  implicit val codec = CodecJson.derive[Expenses]

  def apply(description: String, total: String, numberOfItems: String, items: List[Expense]) = {
    new Expenses(
      description,
      """\d*\.\d{2}""".r.findFirstIn(total).map(_.toDouble).getOrElse(0),
      """\d*""".r.findFirstIn(numberOfItems).map(_.toInt).getOrElse(0),
      items
    )
  }
}

case class Expenses(description: String, total: Double, numberOfItems: Int, items: List[Expense]) {
  def nonEmpty = numberOfItems > 0
}






