package com.example.customers

trait VIPCustomer extends Customer {
  override def discounts = super.discounts() ::: List(10)
}
