package bad.robot

import java.time.LocalDate

case class DateRange(start: LocalDate, end: LocalDate) {
  override def toString(): String = s"${start.toString}_${end.toString}"
}
