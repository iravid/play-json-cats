package com.iravid.playjsoncats

import cats.kernel.{ Eq, Monoid, Semigroup }
import cats.{ Applicative, ApplicativeError, Eval, Monad, MonadError, Traverse }
import play.api.libs.json.{ JsError, JsResult, JsSuccess }

import scala.annotation.tailrec
import scala.language.higherKinds

object JsResultInstances extends JsResultInstances
trait JsResultInstances extends JsResultInstances0 with JsResultInstances1

private[playjsoncats] sealed trait JsResultInstances0 {
  implicit val jsResultApplicativeAndApplicativeError: Applicative[JsResult] with ApplicativeError[JsResult, JsError] =
    new Applicative[JsResult] with ApplicativeError[JsResult, JsError] {
      def pure[A](x: A) = JsSuccess(x)

      override def map[A, B](fa: JsResult[A])(f: A => B): JsResult[B] = fa map f

      def ap[A, B](ff: JsResult[A => B])(fa: JsResult[A]): JsResult[B] =
        (ff, fa) match {
          case (JsSuccess(f, _), JsSuccess(a, _)) => JsSuccess(f(a))
          case (fe: JsError, ae: JsError)         => fe ++ ae
          case (fe: JsError, _)                   => fe
          case (_, ae: JsError)                   => ae
        }

      def raiseError[A](e: JsError): JsResult[A] = e

      def handleErrorWith[A](fa: JsResult[A])(f: JsError => JsResult[A]): JsResult[A] =
        fa match {
          case s @ JsSuccess(_, _) => s
          case e: JsError          => f(e)
        }
    }

  implicit def jsResultEq[A]: Eq[JsResult[A]] = Eq.fromUniversalEquals
}

private[playjsoncats] sealed trait JsResultInstances1 extends JsResultInstances2 {
  implicit val jsResultMonads: MonadError[JsResult, JsError] with Monad[JsResult] =
    new MonadError[JsResult, JsError] with Monad[JsResult] {
      def pure[A](x: A) = JsSuccess(x)

      def flatMap[A, B](fa: JsResult[A])(f: A => JsResult[B]) = fa flatMap f

      @tailrec
      def tailRecM[A, B](a: A)(f: A => JsResult[Either[A, B]]): JsResult[B] =
        f(a) match {
          case e: JsError                => e
          case JsSuccess(Left(nextA), _) => tailRecM(nextA)(f)
          case JsSuccess(Right(b), path) => JsSuccess(b, path)
        }

      override def map[A, B](fa: JsResult[A])(f: A => B): JsResult[B] = fa map f

      def handleErrorWith[A](fa: JsResult[A])(f: JsError => JsResult[A]): JsResult[A] =
        fa match {
          case e: JsError => f(e)
          case _          => fa
        }

      def raiseError[A](e: JsError): JsResult[A] = e
    }

  implicit def jsResultMonoid[A: Monoid]: Monoid[JsResult[A]] = new Monoid[JsResult[A]] {
    def empty: JsResult[A] = JsSuccess(Monoid[A].empty)
    def combine(x: JsResult[A], y: JsResult[A]): JsResult[A] =
      jsResultSemigroup[A].combine(x, y)
  }
}

private[playjsoncats] sealed trait JsResultInstances2 {
  implicit val jsResultTraverse: Traverse[JsResult] = new Traverse[JsResult] {
    override def traverse[G[_]: Applicative, A, B](fa: JsResult[A])(f: A => G[B]): G[JsResult[B]] =
      fa match {
        case e: JsError         => Applicative[G].pure(e)
        case JsSuccess(a, p)    => Applicative[G].map(f(a))(JsSuccess(_, p))
      }

    def foldLeft[A, B](fa: JsResult[A], b: B)(f: (B, A) => B): B =
      fa.map(f(b, _)).getOrElse(b)

    def foldRight[A, B](fa: JsResult[A], lb: Eval[B])(f: (A, Eval[B]) => Eval[B]): Eval[B] =
      fa.map(f(_, lb)).getOrElse(lb)

    override def map[A, B](fa: JsResult[A])(f: A => B): JsResult[B] = fa map f
  }

  implicit def jsResultSemigroup[A: Semigroup] = new Semigroup[JsResult[A]] {
    def combine(x: JsResult[A], y: JsResult[A]): JsResult[A] =
      (x, y) match {
        case (JsSuccess(xv, _), JsSuccess(yv, _)) => JsSuccess(Semigroup[A].combine(xv, yv))
        case (xe: JsError, ye: JsError)           => xe ++ ye
        case (xe: JsError, _)                     => xe
        case (_, ye: JsError)                     => ye
      }
  }
}
