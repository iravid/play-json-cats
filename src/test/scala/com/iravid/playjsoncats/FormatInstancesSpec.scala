package com.iravid.playjsoncats

import org.scalatest.FunSuite
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.typelevel.discipline.scalatest.Discipline
import cats.laws.discipline._
import cats.kernel.instances.all._

import play.api.libs.json.{ Format, Writes, Reads }

class FormatInstancesSpec extends FunSuite with Discipline with GeneratorDrivenPropertyChecks with FormatInstances {
  import Arbitraries._

  checkAll("Format", InvariantTests[Format].invariant[String, String, String])
}

class ReadsInstancesSpec extends FunSuite with Discipline with GeneratorDrivenPropertyChecks with ReadsInstances {
  import Arbitraries._

  checkAll("Reads", FunctorTests[Reads].functor[String, String, String])
  checkAll("Reads", ApplicativeTests[Reads].applicative[String, String, String])
  checkAll("Reads", MonadTests[Reads].monad[String, String, String])
}

class WritesInstancesSpec extends FunSuite with Discipline with GeneratorDrivenPropertyChecks with WritesInstances {
  import Arbitraries._

  checkAll("Reads", ContravariantTests[Writes].contravariant[String, String, String])
}
