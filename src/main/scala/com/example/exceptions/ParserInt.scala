package com.example.exceptions



object Parser extends App {

  def parse(numberAsString: String) = {
    try {
      Integer.parseInt(numberAsString)
    } catch {
      case nfe: NumberFormatException =>
        println("Wrong format for number " + numberAsString)
        // Return an Int or else the type will be inferred as AnyType
        -1

      case _: Throwable =>
        println("Error when parsing number " + numberAsString)
        // Return an Int or else the type will be inferred as AnyType
        -1
    }
  }

  var number = parse("1234")
  println(number.getClass)
  parse("12three")
  println(number.getClass)

}
