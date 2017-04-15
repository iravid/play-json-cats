package com.iravid.playjsoncats
import org.scalacheck.{ Arbitrary, Gen, Cogen }
import play.api.libs.json._

object Arbitraries {
  import JsResultInstances.jsResultEq
  implicit def jsSuccessEq[A]: Eq[JsSuccess[A]] = Eq.by[JsSuccess[A], JsResult[A]](r => r)
  implicit val jsErrorEq: Eq[JsError] = Eq.fromUniversalEquals

  implicit def jsResultArb[A: Arbitrary]: Arbitrary[JsResult[A]] = Arbitrary {
    Gen.oneOf(
      Arbitrary.arbitrary[JsSuccess[A]],
      Arbitrary.arbitrary[JsError]
    )
  }

  implicit def jsSuccessArb[A: Arbitrary]: Arbitrary[JsSuccess[A]] = Arbitrary(Arbitrary.arbitrary[A].map(JsSuccess(_)))
  implicit val jsErrorArb: Arbitrary[JsError] = Arbitrary(Arbitrary.arbitrary[String].map(JsError(_)))
  implicit val jsErrorCogen: Cogen[JsError] = Cogen.it(e => JsError.toJson(e).toString.iterator)
}
