package bad.robot
  
import java.time.LocalDate
import argonaut.CodecJson
import bad.robot.Codecs._

object DateRange {
  implicit val codec = CodecJson.derive[DateRange]
}

case class DateRange(start: LocalDate, end: LocalDate) {
  override def toString(): String = s"${start.toString}_${end.toString}"
}
