package com.example.json

import io.circe.{ Decoder, Encoder }
import java.time.Instant
import scala.util.Try

object Piggyback extends App {
  implicit val encodeInstant: Encoder[Instant] = Encoder.encodeString.contramap[Instant](_.toString)
  implicit val decodeInstant: Decoder[Instant] = Decoder.decodeString.emapTry { str =>
    Try(Instant.parse(str))
  }
}
