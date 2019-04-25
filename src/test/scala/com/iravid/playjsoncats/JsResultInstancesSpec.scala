package com.iravid.playjsoncats

import org.scalatest.FunSuite
import org.typelevel.discipline.scalatest.Discipline
import cats.{ Applicative, ApplicativeError, Functor, Monad, MonadError, Monoid, Traverse }
import cats.kernel.laws.discipline.MonoidTests
import cats.implicits._
import cats.laws.discipline._
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import play.api.libs.json.{ JsError, JsResult }

class JsResultInstancesSpec
    extends FunSuite with Discipline with ScalaCheckDrivenPropertyChecks with JsResultInstances {
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
