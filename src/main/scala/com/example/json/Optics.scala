package com.example.json

/**
 * With cursors, we start with a JSON document, get a cursor from it, then use that cursor to traverse the document.
 *
 * With optics, we first define the traversal then apply it to a JSON document.
 */

import io.circe._, io.circe.parser._
import io.circe.optics.JsonPath._

object Optics extends App {
  val json: Json = parse(
    """
            {
              "order": {
                "customer": {
                  "name": "Custy McCustomer",
                  "contactDetails": {
                    "address": "1 Fake Street, London, England",
                    "phone": "0123-456-789"
                  }
                },
                "items": [{
                  "id": 123,
                  "description": "banana",
                  "quantity": 1
                }, {
                  "id": 456,
                  "description": "apple",
                  "quantity": 2
                }],
                "total": 123.45
              }
            }
            """).getOrElse(Json.Null)


  // Define the traversal.
  val _phoneNum = root.order.customer.contactDetails.phone.string

  // Apply the traversal and get the option value.
  val phoneNum: Option[String] = _phoneNum.getOption(json)
  println(phoneNum)

  // Example using cursor
  val itemsFromCursor: Vector[Json] = json.hcursor
    .downField("order")
    .downField("items")
    .focus
    .flatMap(_.asArray)
    .getOrElse(Vector.empty)
  println(itemsFromCursor)

  val quantities: Vector[Int] = itemsFromCursor.flatMap(_.hcursor.get[Int]("quantity").toOption)
  println(quantities)

  // Same example using optics
  val items: List[Int] = root.order.items.each.quantity.int.getAll(json)
  println(items)


  // Double all the quantities
  val doubleQuantities: Json => Json =
    root.order.items.each.quantity.int.modify(_ * 2)

  val modifiedJson = doubleQuantities(json)
  println(modifiedJson)


  import io.circe.optics.JsonOptics._
  import monocle.function.Plated

  val output = Plated.transform[Json] { j =>
    j.asNumber match {
      case Some(n) => Json.fromString(n.toString)
      case None =>j
    }
  }(json)
  println(output)
}
