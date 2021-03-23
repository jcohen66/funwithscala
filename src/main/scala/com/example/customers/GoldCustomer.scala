package com.example.customers

trait GoldCustomer extends Customer {
  override def discounts(): List[Int] =
    if (discountCode.equals("N"))
      super.discounts() ::: List(20)
    else
      super.discounts() ::: List(15)

}
