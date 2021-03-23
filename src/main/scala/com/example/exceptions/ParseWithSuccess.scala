package com.example.exceptions

object ParseWithSuccess extends App {

  case class Failure(val reason: String)

  def parse(numberAsString: String): Either[Failure, Int] = {
    try {

      val result = Integer.parseInt(numberAsString)
      Right(result)

    } catch {

      case _: Throwable =>
        println("Error when parsing number " + numberAsString)
        Left(Failure("Error when parsing number " + numberAsString))
    }
  }

  var number = parse("23ab")
  println(number.getClass())
  number = parse("1245")
  println(number.getClass())

}
