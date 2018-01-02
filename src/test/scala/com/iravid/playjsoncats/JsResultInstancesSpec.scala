package com.iravid.playjsoncats

import org.scalacheck.{ Arbitrary, Cogen, Gen }
import org.scalatest.FunSuite
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.typelevel.discipline.scalatest.Discipline

import cats.{ Functor, Applicative, ApplicativeError, Monad, MonadError, Traverse, Monoid }
import cats.kernel.Eq
import cats.kernel.laws.discipline.MonoidTests
import cats.implicits._
import cats.laws.discipline._

import play.api.libs.json.{ JsError, JsResult, JsSuccess }

class JsResultInstancesSpec extends FunSuite with Discipline with GeneratorDrivenPropertyChecks with JsResultInstances {
  import Arbitraries._

  implicit val iso = SemigroupalTests.Isomorphisms.invariant[JsResult]

  checkAll("JsResult", MonadTests[JsResult].monad[String, String, String])
  checkAll("JsResult", ApplicativeTests[JsResult].applicative[String, String, String])
  checkAll("JsResult", MonadErrorTests[JsResult, JsError].monadError[Int, Int, Int])
  checkAll("JsResult", ApplicativeErrorTests[JsResult, JsError].applicativeError[Int, Int, Int])
  checkAll("JsResult", TraverseTests[JsResult].traverse[Int, Int, Int, Int, Option, Option])
  checkAll("JsResult", SemigroupalTests[JsResult].semigroupal[Int, Int, Int])
  checkAll("JsResult", MonoidTests[JsResult[String]].monoid)

  {
    Functor[JsResult]
    Applicative[JsResult]
    ApplicativeError[JsResult, JsError]
    Monad[JsResult]
    MonadError[JsResult, JsError]
    Traverse[JsResult]
    Monoid[JsResult[String]]
  }
}
