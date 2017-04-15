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


  implicit val jsValueEq: Eq[JsValue] = Eq.fromUniversalEquals

  val maxSize = 4

  implicit val jsValueArbitrary: Arbitrary[JsValue] = Arbitrary {
    Gen.oneOf(
      Gen.identifier.map(JsString(_)),
      Gen.posNum[Long].map(JsNumber(_)),
      Gen.oneOf(true, false).map(JsBoolean(_)),
      Gen.lzy(Arbitrary.arbitrary[JsObject]),
      Gen.lzy(Arbitrary.arbitrary[JsArray])
    )
  }

  implicit val jsObjectArbitrary: Arbitrary[JsObject] = Arbitrary {
    Gen.lzy {
      val keyGen = Gen.identifier

      Gen.resize(maxSize, Gen.listOf {
        for {
          value <- Arbitrary.arbitrary[JsValue]
          key <- keyGen
        } yield (key, value)
      } map (JsObject(_)))
    }
  }

  implicit val jsArrayArbitrary: Arbitrary[JsArray] = Arbitrary {
    Gen.lzy(Gen.resize(maxSize, Gen.listOf(Arbitrary.arbitrary[JsValue]).map(JsArray(_))))
  }

  implicit val jsValueCogen: Cogen[JsValue] = Cogen { (seed, js) =>
    js match {
      case JsString(s)  => Cogen.perturb(seed, s)
      case JsBoolean(b) => Cogen.perturb(seed, b)
      case JsNumber(n)  => Cogen.perturb(seed, n)
      case JsArray(vs)  => Cogen.perturb(seed, vs)
      case o: JsObject  => Cogen.perturb(seed, o.fields)
      case JsNull       => Cogen.perturb(seed, ())
    }
  }
}
