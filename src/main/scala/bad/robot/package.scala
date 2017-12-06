package bad

import java.io.File
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import argonaut.Argonaut.{jNull, jString}
import argonaut.{DecodeJson, DecodeResult, EncodeJson}

import scala.util.{Failure, Success, Try}

package object robot {

  implicit class FileOps(file: File) {
    def /(child: String): File = new File(file, child)
  }

  implicit class PeriodOps(period: String) {
    def toDateRange: Either[String, DateRange] = DateRangeParser.parse(period)
  }

  implicit val localDateEncoder: EncodeJson[LocalDate] =
    EncodeJson((date: LocalDate) => if (date == null) jNull else jString(date.toString))

  implicit val localDateDecoder: DecodeJson[LocalDate] =
    DecodeJson(cursor => {
      val toDate: String => DecodeResult[LocalDate] = value => {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

        Try(LocalDate.parse(value, formatter)) match {
          case Failure(error) => DecodeResult.fail[LocalDate](error.getMessage, cursor.history)
          case Success(date)  => DecodeResult.ok(date)
        }
      }
      cursor.as[String].flatMap(toDate)
    })

}

