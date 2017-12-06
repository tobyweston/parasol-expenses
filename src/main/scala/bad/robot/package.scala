package bad

import java.io.File

package object robot {

  implicit class FileOps(file: File) {
    def /(child: String): File = new File(file, child)
  }

  implicit class PeriodOps(period: String) {
    def toDateRange: Either[String, DateRange] = DateRangeParser.parse(period)
  }
}

