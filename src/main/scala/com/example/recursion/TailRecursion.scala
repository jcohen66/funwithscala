package com.example.recursion

import scala.annotation.tailrec

object TailRecursion extends App {
  /**
   * Simple recursion will cause scala to make the call stack many levels deep.
   * @param list
   * @return
   */
  def recursiveLength(list: List[String]): Long = list match {
    case Nil => 0
    case head::tail => 1 + recursiveLength(tail)
  }

  /**
   * The last call of the method must be the recursive call.
   * This will tell scala to reduce the call stack depth down to one call.
   * @param list
   * @param accumulator
   * @return
   */
  @tailrec
  def tailRecursiveLength(list: List[String], accumulator: Long): Long = list match {
    case Nil => 0
    case head::tail => tailRecursiveLength(tail, accumulator + 1)
  }

  val list = List("One", "Two", "Three")
  println(recursiveLength(list))
  println(tailRecursiveLength(list, 0))
}
