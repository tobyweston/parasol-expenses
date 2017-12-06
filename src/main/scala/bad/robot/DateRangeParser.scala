package bad.robot

import java.time.{LocalDate, Month}

object DateRangeParser {

  private val Day    = """(\d{2})"""
  private val Hyphen = "-"
  private val Month  = "([a-zA-Z]{3})"
  private val Year   = """(\d{4})"""
  private val To     = " to "

  private val Period = (Day + Hyphen + Month + To + Day + Hyphen + Month + Hyphen + Year).r


  def parse(period: String): Either[String, DateRange] = {

    period match {
      case Period(startDay, startMonth, endDay, endMonth, endYear) => Right(DateRange(toDate(startDay, startMonth, endYear), toDate(endDay, endMonth, endYear)))
      case data @ _                                                => Left(s"Could not parse period '$data' to a date range")
    }
  }

  private def toDate(day: String, month: String, year: String) = LocalDate.of(year.toInt, NumericMonth(month), day.toInt)

  object NumericMonth {
    import java.text.SimpleDateFormat
    import java.util.Calendar

    private val AbbreviatedMonthString = new SimpleDateFormat("MMM")
    private val NumericMonth = new SimpleDateFormat("MM")
    private val calendar = Calendar.getInstance

    def apply(month: String): Month = {
      calendar.setTime(AbbreviatedMonthString.parse(month))
      java.time.Month.of(NumericMonth.format(calendar.getTime).toInt)
    }
  }

}
