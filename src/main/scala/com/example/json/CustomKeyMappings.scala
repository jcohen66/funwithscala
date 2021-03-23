package com.example.json

import io.circe.Encoder, io.circe.syntax._

case class User(firstName: String, lastName: String)
case class Bar(i: Int, s: String)



object CustomKeyMappings extends App {
  implicit val encodeUser: Encoder[User] = Encoder.forProduct2("first_name", "last_name")(u => (u.firstName, u.lastName))
  implicit val encodeBar: Encoder[Bar] = Encoder.forProduct2("my-int", "s")(b => (b.i, b.s))

  User("Foo", "McBar").asJson
  Bar(13, "Qux").asJson

}
