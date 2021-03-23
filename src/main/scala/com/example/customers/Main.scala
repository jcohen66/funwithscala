package com.example.customers

object Main {
  def main(args: Array[String]): Unit = {
    // Note the order in which traits are stacked is right to left so GoldCustomer is called first.
    val myDiscounts = new Customer("Thomas", "N") with VIPCustomer with GoldCustomer
    println(myDiscounts)

    val myDefault = Customer()
    println(myDefault)
  }

}
