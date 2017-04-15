package com.iravid.playjsoncats

import cats.kernel.Eq
import play.api.libs.json.{ Json, JsObject, JsArray }
import cats.Monoid

object JsObjectInstances extends JsObjectInstances
trait JsObjectInstances {
  implicit val jsObjectMonoid: Monoid[JsObject] = new Monoid[JsObject] {
    def empty: JsObject = Json.obj()
    def combine(x: JsObject, y: JsObject): JsObject = x deepMerge y
  }

  implicit val jsObjectEq: Eq[JsObject] = Eq.fromUniversalEquals
}

object JsArrayInstances extends JsArrayInstances
trait JsArrayInstances {
  implicit val jsArrayMonoid: Monoid[JsArray] = new Monoid[JsArray] {
    def empty: JsArray = Json.arr()
    def combine(x: JsArray, y: JsArray): JsArray = x ++ y
  }

  implicit val jsArrayEq: Eq[JsArray] = Eq.fromUniversalEquals
}
