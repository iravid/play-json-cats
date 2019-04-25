package com.iravid.playjsoncats

import cats.kernel.laws.discipline.MonoidTests
import org.scalatest.FunSuite
import org.typelevel.discipline.scalatest.Discipline
import cats.{ Monoid, Semigroup }
import play.api.libs.json._
import JsObjectInstances._
import JsArrayInstances._
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks

class JsValueInstancesSpec extends FunSuite with Discipline with ScalaCheckDrivenPropertyChecks {
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
