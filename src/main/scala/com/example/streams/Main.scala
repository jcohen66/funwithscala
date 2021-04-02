package com.example.streams
import scala.math.BigInt

object Main extends App {
  val fibs: LazyList[BigInt] =
    BigInt(0) #:: BigInt(1) #::
      fibs.zip(fibs.tail).map{ n =>
        println(s"Adding ${n._1} and ${n._2}")
        n._1 + n._2
      }
  fibs.take(5).foreach(println)
  fibs.take(6).foreach(println)
}
