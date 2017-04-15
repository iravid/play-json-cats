package com.iravid.playjsoncats

import org.scalacheck.{ Arbitrary, Cogen, Gen }
import org.scalatest.FunSuite
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.typelevel.discipline.scalatest.Discipline

import cats.{ Functor, Applicative, ApplicativeError, Monad, MonadError, Traverse, TraverseFilter, Monoid }
import cats.kernel.Eq
import cats.kernel.laws.GroupLaws
import cats.implicits._
import cats.laws.discipline._

import play.api.libs.json.{ JsError, JsResult, JsSuccess }

class JsResultInstancesSpec extends FunSuite with Discipline with GeneratorDrivenPropertyChecks with JsResultInstances {
  import Arbitraries._

  implicit val iso = CartesianTests.Isomorphisms.invariant[JsResult]

  checkAll("JsResult", MonadTests[JsResult].monad[String, String, String])
  checkAll("JsResult", ApplicativeTests[JsResult].applicative[String, String, String])
  checkAll("JsResult", MonadErrorTests[JsResult, JsError].monadError[Int, Int, Int])
  checkAll("JsResult", ApplicativeErrorTests[JsResult, JsError].applicativeError[Int, Int, Int])
  checkAll("JsResult", TraverseTests[JsResult].traverse[Int, Int, Int, Int, Option, Option])
  checkAll("JsResult", TraverseFilterTests[JsResult].traverseFilter[Int, Int, Int, Int, Option, Option])
  checkAll("JsResult", CartesianTests[JsResult].cartesian[Int, Int, Int])
  checkAll("JsResult", GroupLaws[JsResult[String]].monoid)

  {
    Functor[JsResult]
    Applicative[JsResult]
    ApplicativeError[JsResult, JsError]
    Monad[JsResult]
    MonadError[JsResult, JsError]
    Traverse[JsResult]
    TraverseFilter[JsResult]
    Monoid[JsResult[String]]
  }
}
