package com.example.json

import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder}

object SemiautomaticDerivation extends App {

  case class Foo(a: Int, b: String, c: Boolean)

  implicit val fooDecoder: Decoder[Foo] = deriveDecoder[Foo]
  implicit val fooEncoder: Encoder[Foo] = deriveEncoder[Foo]

  case class Bar(i: Int, s: String)

  implicit val barDecoder: Decoder[Bar] = deriveDecoder[Bar]
  implicit val barEncoder: Encoder[Bar] = deriveEncoder[Bar]

  val bar = Bar(13, "Qux").asJson
  println(bar)

  case class User(id: Long, firstName: String, lastName: String)

  implicit val decodeUser: Decoder[User] = Decoder.forProduct3("id", "first_name", "last_name")(User.apply)

  implicit val encodeUser: Encoder[User] = Encoder.forProduct3("id", "first_name", "last_name")(u =>
    (u.id, u.firstName, u.lastName)
  )

  val employee = User(1, "Tim", "Scott")
  val employeeJson = employee.asJson
  print(employeeJson)
  val employeeObj = employeeJson.asObject
  print(employeeObj)
}
