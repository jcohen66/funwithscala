package com.example.folding

import scala.Double.NaN

object Folding extends App {
  def sum1(list: List[Int]): Int = list.foldLeft(0)((r, c) => r + c)
  def sum2(list: List[Int]): Int = list.foldLeft(0)(_ + _)

  def product(list: List[Int]): Int = list.foldLeft(1)(_ * _)

  // Discard the item and add 1 to the result
  def count(list: List[Any]): Int = list.foldLeft(0)((sum, _) => sum + 1)

  // Sum / Count
  def average1(list: List[Double]): Double = list.foldLeft(0.0)(_ + _) / list.foldLeft(0)((r,c) => r + 1)
  def average2(list: List[Double]): Double = list match {
    case head :: tail => tail.foldLeft( (head, 1.0))((r, c) =>
      ((r._1 + (c/r._2)) * r._2 / (r._2 + 1), r._2 + 1))._1
    case Nil => NaN
  }

  // Take an item of type A and return an item of type A.
  // Discard the accumulator and return only the current item.
  def last[A](list: List[A]): A = list.foldLeft[A](list.head)((_, c) => c)

  // Maintain a tuple pair of the previous and current items.  The last tuple contains
  // the next to last and last items.  The penultimate item is mext to last (._1)
  def penultimate[A](list: List[A]): A = list.foldLeft((list.head, list.tail.head))((r,c) => (r._2, c))._1

  // ORs the accumulator with the current item and compares against the target item.
  // If item matches target, the accumulator remains True.
  def contains[A](list: List[A], item: A): Boolean = list.foldLeft(false)(_ || _ == item)

  // Look up a value in the list by index.
  // Use a tuple pair to compare index.
  def get[A](list: List[A], idx: Int): A =
    list.tail.foldLeft((list.head, 0)) {
      (r, c) => if (r._2 == idx) r else (c, r._2+1)
    } match {
      case (result, index) if (idx == index) => result
      case _ => throw new Exception("Bad index")
    }

  def mimicToString[A](list: List[A]): String = list match {
    case head :: tail => tail.foldLeft("List(" + head)(_ + ", " + _) + ")"
    case Nil => "List()"
  }

  // Start with an empty list.
  // Just switch the item and accumulator order.
  def reverse[A](list: List[A]): List[A] =
    list.foldLeft(List[A]())((r,c) => c :: r)

  // Start with an empty list.  Look at each item and if its already contained
  // then it stays as is.  If its not in the accumulator then its appended.
  def unique1[A](list:List[A]): List[A] =
    list.foldLeft(List[A]()) { (r,c) =>
      if (r.contains(c)) r else c :: r
    }.reverse

  // Start with an empty list.  Check if the item exists
  // if it does, just add the accumulator otherwise add
  // the item cons accumulator.
  def unique2[A](list: List[A]): List[A] =
    list.foldLeft(List[A]()) { (r,c) =>
      if (r.contains(c)) r else c :: r
    }.foldLeft(List[A]())((r,c) => c :: r)

  // Start with an empty Set.  Add the accumulator + item.
  def toSet[A](list: List[A]): Set[A] = list.foldLeft(Set[A]())((r, c) => r + c)

  // Each item appears twice.
  def double[A](list: List[A]): List[A] =
    list.foldLeft(List[A]())((r, c) => c :: c :: r).reverse

  /**
   * The type parameter ensures that we have elements that can be arranged in order.
   * We start with an empty list as the initial accumulator.  Then for each item we assume
   * the accumulator is in sorted order and use span() to split it into two sub lists:
   * - all already-sorted items less than the current item
   * - all already-sorted items greater than or equal to  the current item
   * We put the current item in between these two and the accumulator remains sorted.
   *
   *
   * @param list
   * @tparam A
   * @return
   */
  def insertionSort[A <% Ordered[A]](list: List[A]): List[A] =
    list.foldLeft(List[A]()) { (r, c) =>
      val (front, back) = r.span(_ < c)
      front ::: c :: back
    }

  // Takes triplet.
  def pivot[A <% Ordered[A]](list: List[A]): (List[A], A, List[A]) =
    list.tail.foldLeft[(List[A], A, List[A])]((Nil, list.head, Nil)){
      (result, item) =>
        val (r1, pivot, r2) = result
        if (item < pivot) (item :: r1, pivot, r2) else (r1, pivot, item :: r2)
    }

  def encode[A](list: List[A]): List[(A, Int)] =
    list.foldLeft(List[(A, Int)]()) { (r, c) =>
      r match {
        case (value, count) :: tail =>
          if (value == c) (c, count+1) :: tail
          else (c, 1) :: r
        case Nil =>
          (c, 1) :: r
      }
    }.reverse

  def decode[A](list: List[(A, Int)]): List[A] =
    list.foldLeft(List[A]()) { (r,c) =>
      var result = r
      for (_ <- 1 to c._2) result = c._1 :: result
      result
    }.reverse

  def group[A](list: List[A], size: Int): List[List[A]] =
    list.foldLeft((List[List[A]](),0)) { (r,c) => r match {
      case (head::tail, num) =>
        if (num < size) ((c :: head) :: tail, num + 1)
        else (List(c) :: head :: tail, 1)
      case (Nil, num) => (List(List(c)), 1)
    }
    }._1.foldLeft(List[List[A]]())( (r,c) => c.reverse :: r )
}
