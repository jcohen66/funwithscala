package com.example.iters

object MapOver extends App {

  val amounts = List(3, 6, 7, 10) map (amt => amt + 1)
  val otherAmounts = amounts map(_ + 1)

  val sumOfAmounts = amounts.foldLeft(0) (_ + _)
  val sumOfAmounts2 = amounts.foldLeft(0) {(accumulator,item) => (accumulator + item)}

  val sumOfAmounts3 = amounts.reduce(_ + _)
  val sumOfAmounts4 = amounts.reduce((total, item) => total + item)
}
