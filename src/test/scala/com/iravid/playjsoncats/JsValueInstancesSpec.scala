package com.iravid.playjsoncats

import cats.kernel.laws.discipline.MonoidTests
import org.scalatest.FunSuite
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.typelevel.discipline.scalatest.Discipline

import cats.{ Monoid, Semigroup }
import play.api.libs.json._
import JsObjectInstances._
import JsArrayInstances._

class JsValueInstancesSpec extends FunSuite with Discipline with GeneratorDrivenPropertyChecks {
  import Arbitraries._

  checkAll("JsObject", MonoidTests[JsObject].monoid)
  checkAll("JsArray", MonoidTests[JsArray].monoid)

  {
    Monoid[JsObject]
    Semigroup[JsObject]
    Monoid[JsArray]
    Semigroup[JsArray]
  }
}
