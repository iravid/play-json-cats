package com.iravid.playjsoncats

import cats.kernel.laws.GroupLaws
import org.scalacheck.{ Arbitrary, Gen }
import org.scalatest.FunSuite
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.typelevel.discipline.scalatest.Discipline

import cats.{ Monoid, Semigroup }
import cats.implicits._
import cats.laws.discipline._
import cats.laws.discipline.arbitrary._
import play.api.libs.json._
import JsObjectInstances._
import JsArrayInstances._

class JsValueInstancesSpec extends FunSuite with Discipline with GeneratorDrivenPropertyChecks {
  import Arbitraries._

  checkAll("JsObject", GroupLaws[JsObject].monoid)
  checkAll("JsArray", GroupLaws[JsArray].monoid)

  {
    Monoid[JsObject]
    Semigroup[JsObject]
    Monoid[JsArray]
    Semigroup[JsArray]
  }
}
