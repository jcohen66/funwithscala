package com.example.comprehensions

object words extends App {
  // With multiple generators
  var chars = for {
    word <- List("Hello", "Scala")
    char <- word
  } yield char
  println(chars)

  // With guard/filter
  chars = for {
    word <- List("Hello", "Scala")
    char <- word if char.isUpper
  } yield char
  println(chars)

  // with local variable
  chars = for {
    word <- List("Hello", "Scala")
    char <- word if char.isUpper
    lowerChar = char.toLower
  } yield lowerChar
  println(chars)
}
