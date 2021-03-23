package com.example.json

import io.circe.generic.auto._, io.circe.syntax._

object AutomaticDerivation extends App {
  case class Person(name: String)
  case class Greeting(salutation: String, person: Person, exclamationMarks: Int)

  val greeting = Greeting("Hey", Person("Chris"), 3).asJson

  println(greeting)
}
