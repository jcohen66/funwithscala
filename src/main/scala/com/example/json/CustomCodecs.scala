package com.example.json

import io.circe.{Decoder, Encoder, HCursor, Json}

object CustomCodecs extends App {
  class Thing(val foo: String, val bar: Int)

  implicit val encodeFoo: Encoder[Thing] = new Encoder[Thing] {
    override def apply(a: Thing): Json = Json.obj(
      ("foo", Json.fromString(a.foo)),
      ("bar", Json.fromInt(a.bar))
    )
  }

  implicit val decodeFoo: Decoder[Thing] = new Decoder[Thing] {
    override def apply(c: HCursor): Decoder.Result[Thing] =
      for {
        foo <- c.downField("foo").as[String]
        bar <- c.downField("bar").as[Int]
      } yield {
        new Thing(foo, bar)
      }
  }

  val thing = new Thing("foo", 1)

}
