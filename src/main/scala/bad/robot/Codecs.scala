package bad.robot

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import argonaut.Argonaut.{jNull, jString, _}
import argonaut.{DecodeJson, DecodeResult, EncodeJson}

import scala.util.{Failure, Success, Try}

object Codecs {
  
  implicit def localDateEncoder: EncodeJson[LocalDate] = {
    EncodeJson((date: LocalDate) => if (date == null) jNull else jString(date.toString))
  }
  
  implicit def localDateDecoder: DecodeJson[LocalDate] = {
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

  implicit def eitherDateRangeEncoder: EncodeJson[Either[String, DateRange]] = {
    EncodeJson((either: Either[String, DateRange]) =>
      argonaut.Json(
        "period" := either.map(_.toString).getOrElse(either.left.get)
      )
    )
  }
}
