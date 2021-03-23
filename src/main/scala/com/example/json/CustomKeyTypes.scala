package com.example.json

import io.circe._, io.circe.syntax._

case class Foo(value: String)

object CustomKeyTypes extends App {
  implicit val fooKeyEncoder: KeyEncoder[Foo] = new KeyEncoder[Foo] {
    override def apply(key: Foo): String = key.value
  }

  val map = Map[Foo, Int](
    Foo("hello") -> 123,
    Foo("world") -> 456
  )

  val json = map.asJson
  println(json)

  implicit val fooKeyDecoder: KeyDecoder[Foo] = new KeyDecoder[Foo] {
    override def apply(key: String): Option[Foo] = Some(Foo(key))
  }

  val obj = json.as[Map[Foo, Int]]
  println(obj)

}
