package com.iravid.playjsoncats

import cats.{ Applicative, Monad }
import cats.{ Contravariant, Invariant }
import play.api.libs.json.{ Format, JsError, JsResult, JsSuccess, JsValue, Reads, Writes }

import scala.annotation.tailrec

object ReadsInstances extends ReadsInstances
trait ReadsInstances extends ReadsInstances0 with ReadsInstances1

private[playjsoncats] sealed trait ReadsInstances0 {
  implicit val readsApplicative: Applicative[Reads] = new Applicative[Reads] {
    def pure[A](x: A): Reads[A] = Reads(_ => JsSuccess(x))

    def ap[A, B](ff: Reads[A => B])(fa: Reads[A]): Reads[B] =
      Reads { json =>
        (ff reads json, fa reads json) match {
          case (JsSuccess(f, _), JsSuccess(a, _)) => JsSuccess(f(a))
          case (fe: JsError, ae: JsError)         => fe ++ ae
          case (fe: JsError, _)                   => fe
          case (_, ae: JsError)                   => ae
        }
      }

    override def map[A, B](fa: Reads[A])(f: A => B): Reads[B] = fa map f
  }
}

private[playjsoncats] sealed trait ReadsInstances1 {
  implicit val readsMonad: Monad[Reads] = new Monad[Reads] {
    def pure[A](x: A): Reads[A] = Reads(_ => JsSuccess(x))

    def flatMap[A, B](fa: Reads[A])(f: A => Reads[B]) =
      fa.flatMap(f)

    def tailRecM[A, B](a: A)(f: A => Reads[Either[A, B]]): Reads[B] =
      new Reads[B] {
        @tailrec
        def step(json: JsValue, currentA: A): JsResult[B] =
          f(currentA).reads(json) match {
            case e: JsError                => e
            case JsSuccess(Left(nextA), _) => step(json, nextA)
            case JsSuccess(Right(b), path) => JsSuccess(b, path)
          }

        def reads(json: JsValue): JsResult[B] = step(json, a)
      }

    override def map[A, B](fa: Reads[A])(f: A => B): Reads[B] = fa map f
  }
}

object WritesInstances extends WritesInstances
trait WritesInstances {
  implicit val writesContravariant: Contravariant[Writes] = new Contravariant[Writes] {
    def contramap[A, B](fa: Writes[A])(f: B => A): Writes[B] =
      Writes(b => fa.writes(f(b)))
  }
}

object FormatInstances extends FormatInstances
trait FormatInstances {
  implicit val formatInvariant: Invariant[Format] = new Invariant[Format] {
    def imap[A, B](fa: Format[A])(f: A => B)(g: B => A): Format[B] =
      Format(Reads(json => fa.reads(json).map(f)), Writes(b => fa.writes(g(b))))
  }
}
