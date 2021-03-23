package com.example.customers

object Customer {
  def apply() = new Customer("default name")
}

class Customer(val name: String, val discountCode:String = "N") {
  def discounts() : List[Int] = List(5)
  override def toString() = "Applied discounts: " + discounts.mkString(" ","%, ", "% ")
}