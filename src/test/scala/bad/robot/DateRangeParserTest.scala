package bad.robot

import java.time.LocalDate

import org.specs2.mutable.Specification

class DateRangeParserTest extends Specification {

  "Parses period" >> {
    "23-Jan to 29-Jan-2017".toDateRange must beRight(DateRange(LocalDate.of(2017, 1, 23), LocalDate.of(2017, 1, 29)))
    "20-Feb to 26-Feb-2017".toDateRange must beRight(DateRange(LocalDate.of(2017, 2, 20), LocalDate.of(2017, 2, 26)))
    "01-Mar to 06-Mar-2016".toDateRange must beRight(DateRange(LocalDate.of(2016, 3, 1), LocalDate.of(2016, 3, 6)))
    "04-Apr to 10-Apr-2016".toDateRange must beRight(DateRange(LocalDate.of(2016, 4, 4), LocalDate.of(2016, 4, 10)))
    "02-May to 08-May-2016".toDateRange must beRight(DateRange(LocalDate.of(2016, 5, 2), LocalDate.of(2016, 5, 8)))
    "06-Jun to 12-Jun-2016".toDateRange must beRight(DateRange(LocalDate.of(2016, 6, 6), LocalDate.of(2016, 6, 12)))
    "04-Jul to 10-Jul-2016".toDateRange must beRight(DateRange(LocalDate.of(2016, 7, 4), LocalDate.of(2016, 7, 10)))
    "04-Aug to 10-Aug-2016".toDateRange must beRight(DateRange(LocalDate.of(2016, 8, 4), LocalDate.of(2016, 8, 10)))
  }

  "Invliad inpirt" >> {
    "rubbish".toDateRange must beLeft(s"Could not parse period 'rubbish' to a date range")
  }
}
