package bad.robot
  
import java.time.LocalDate

import argonaut.Argonaut._
import argonaut._
import bad.robot.Codecs._

object DateRange {
  
  implicit def dateRangeEncodeJson: EncodeJson[DateRange] = jencode2L((range: DateRange) => (range.start, range.end))("start", "end")
  
  implicit def dateRangeDecodeJson: DecodeJson[DateRange] = jdecode2L(DateRange.apply)("start", "end")
}

case class DateRange(start: LocalDate, end: LocalDate) {
  override def toString(): String = s"${start.toString}_${end.toString}"
}
