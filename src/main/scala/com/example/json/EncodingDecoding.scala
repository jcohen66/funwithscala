package com.example.json

import io.circe.parser._
import io.circe.syntax._
import io.circe.{Json, _}


case class Person(name: String, address: Address)
case class Address(city: String, state: String)
case class Roster(var people: List[Person])

object EncoderDecoder extends App {

  def simpleEncode:Unit = {
    val intsJson = List(1,2,3,4).asJson
    println(intsJson)
  }

  def simpleDecode:Unit = {
    val intsJson = List(1,2,3,4).asJson
    println(intsJson.as[List[Int]])
  }

  def simpleDecode2:Unit = {
    val dec = decode[List[Int]]("[1, 2, 3, 4]")
    println(dec)
  }

  val json: String = """
    {
      "id": "c730433b-082c-4984-9d66-855c243266f0",
      "name": "Foo",
      "counts": [1,2,3],
      "values": {
        "bar": true,
        "baz": 100.001,
        "qux": ["a", "b"]
      }
    }
  """

  val doc: Json = parse(json).getOrElse(Json.Null)
  println(doc)

  // In order to traverse, create an HCursor pointing to the doc root.
  val cursor: HCursor = doc.hcursor

  // Returns a Right
  val baz: Decoder.Result[Double] = cursor.downField("values").downField("baz").as[Double]
  baz match {
    case Left(s) => println("No match: " + s)
    case Right(a) => println(a)
  }

  val bazValue = baz match {
    case Right(a) => a
    case Left(s) => ""
  }
  println(bazValue)

  // Returns a Right
  val baz2: Decoder.Result[Double] = cursor.downField("values").get[Double]("baz")

  val secondQux: Decoder.Result[String] = cursor.downField("values").downField("qux").downArray.as[String]
  val secondQuxValue = secondQux match {
    case Right(a) => a
    case Left(s) => " "
  }
  println(secondQuxValue)

  // Transform Data

  // Use cursor to modify a field
  val reversedNameCursor: ACursor = cursor.downField("name").withFocus(_.mapString(_.reverse))

  // The move the cursor back to the root.
  val reversedName: Option[Json] = reversedNameCursor.top
  reversedName match {
    case Some(a) => println(a)
    case _ => println("No match")
  }

  simpleEncode
  simpleDecode
  simpleDecode2

}
