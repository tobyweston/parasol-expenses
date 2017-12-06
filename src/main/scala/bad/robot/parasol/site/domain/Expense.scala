package bad.robot.parasol.site.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Expense {
  def apply(date: String, amount: String, description: Option[String] = None) = {
    val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
    val sanitisedDate = date.replaceAll("""(?<=\d)(st|nd|rd|th)""", "")
    val amountAsDouble = """\d*\.\d{2}""".r.findFirstIn(amount).map(_.toDouble).getOrElse(0D)

    new Expense(LocalDate.parse(sanitisedDate, formatter), amountAsDouble, description)
  }
}

case class Expense(date: LocalDate, amount: Double, description: Option[String])