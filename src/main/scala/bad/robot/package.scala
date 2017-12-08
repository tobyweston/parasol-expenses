package bad

import java.io.File
import java.nio.file.{Files, Path}
import java.util.stream.Collectors

import scala.collection.JavaConverters._


package object robot {

  implicit class FileOps(file: File) {
    def /(child: String): File = new File(file, child)
    def find(filename: String): List[Path] = {
      Files.walk(file.toPath).filter(_.getFileName.endsWith(filename)).collect(Collectors.toList()).asScala.toList
    }
  }

  implicit class PeriodOps(period: String) {
    def toDateRange: Either[String, DateRange] = DateRangeParser.parse(period)
  }
  
}
