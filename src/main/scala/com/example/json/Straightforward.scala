package com.example.json

import cats.syntax.functor._
import io.circe.{ Decoder, Encoder }, io.circe.generic.auto._
import io.circe.syntax._

sealed trait Event

case class Fooo(i: Int) extends Event
case class Barr(s: String) extends Event
case class Bazz(c: Char) extends Event
case class Quxx(values: List[String]) extends Event

object GenericDerivation {
  implicit val encodeEvent: Encoder[Event] = Encoder.instance {
    case foo @ Fooo(_) => foo.asJson
    case bar @ Barr(_) => bar.asJson
    case baz @ Bazz(_) => baz.asJson
    case qux @ Quxx(_) => qux.asJson
  }

  implicit val decodeEvent: Decoder[Event] =
    List[Decoder[Event]](
      Decoder[Fooo].widen,
      Decoder[Barr].widen,
      Decoder[Bazz].widen,
      Decoder[Quxx].widen
    ).reduceLeft(_ or _)
}

object Straightforward extends App {
  import GenericDerivation._
  import io.circe.parser.decode

  val json = decode[Event]("""{ "i": 1000 }""")

  val obj = (Fooo(100): Event).asJson.noSpaces
}
