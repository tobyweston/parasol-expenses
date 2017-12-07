package bad.robot.parasol.site.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import bad.robot.Codecs._

import argonaut.CodecJson

object Expense {

  implicit val codec = CodecJson.derive[Expense]

  private val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")

  def apply(date: String, amount: String, description: Option[String] = None) = {
    new Expense(parseDate(date), parseAmount(amount), description)
  }

  private def parseDate(date: String) = {
    val lastDateInListOfDates = date.split(",").reverse.head.trim
    val sanitisedDate = lastDateInListOfDates.replaceAll("""(?<=\d)(st|nd|rd|th)""", "")
    LocalDate.parse(sanitisedDate, formatter)
  }

  private def parseAmount(amount: String) = {
    """\d*\.\d{2}""".r.findFirstIn(amount).map(_.toDouble).getOrElse(0D)
  }
}

case class Expense(date: LocalDate, amount: Double, description: Option[String])