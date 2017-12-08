package bad.robot.parasol.site.domain

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import bad.robot.Codecs._

import argonaut.CodecJson
import bad.robot.parasol.Amount

object Expense {

  implicit val codec = CodecJson.derive[Expense]

  private val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy")

  def apply(date: String, amount: String, description: Option[String] = None) = {
    new Expense(parseDate(date), Amount.parse(amount), description)
  }

  private def parseDate(date: String) = {
    val lastDateInListOfDates = date.split(",").reverse.head.trim
    val sanitisedDate = lastDateInListOfDates.replaceAll("""(?<=\d)(st|nd|rd|th)""", "")
    LocalDate.parse(sanitisedDate, formatter)
  }
  
}

case class Expense(date: LocalDate, amount: Double, description: Option[String])