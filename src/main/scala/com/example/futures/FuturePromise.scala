package com.example.futures

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global


object FuturePromise extends App {
  implicit val ec = scala.concurrent.ExecutionContext.global

  // Future is a read-only placeholder for the result of an ongoing computation.
  val result = Future {
    // Inside the apply() function of the Future...
    println("Long running computation started.")
    val result = {
      Thread.sleep(5000)
      5
    }
    println("Long running computation finished.")
    result
  }(ec)


  val r1 = Future{ /* computation */ } (ec)
  val r2 = Future{ /* computation */ }

  while(true) {}

}
