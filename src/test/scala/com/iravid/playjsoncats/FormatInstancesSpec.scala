package com.iravid.playjsoncats

import org.scalatest.FunSuite
import org.typelevel.discipline.scalatest.Discipline
import cats.laws.discipline._
import cats.kernel.instances.all._
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import play.api.libs.json.{ Format, Reads, Writes }

class FormatInstancesSpec
    extends FunSuite with Discipline with ScalaCheckDrivenPropertyChecks with FormatInstances {
  import Arbitraries._

  checkAll("Format", InvariantTests[Format].invariant[String, String, String])
}

class ReadsInstancesSpec
    extends FunSuite with Discipline with ScalaCheckDrivenPropertyChecks with ReadsInstances {
  import Arbitraries._

  checkAll("Reads", FunctorTests[Reads].functor[String, String, String])
  checkAll("Reads", ApplicativeTests[Reads].applicative[String, String, String])
  checkAll("Reads", MonadTests[Reads].monad[String, String, String])
}

class WritesInstancesSpec
    extends FunSuite with Discipline with ScalaCheckDrivenPropertyChecks with WritesInstances {
  import Arbitraries._

  checkAll("Reads", ContravariantTests[Writes].contravariant[String, String, String])
}
