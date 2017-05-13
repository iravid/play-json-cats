[![Build Status](https://travis-ci.org/iravid/play-json-cats.svg?branch=master)](https://travis-ci.org/iravid/play-json-cats)

# play-json-cats

`cats` typeclass instances for the `play-json` library.

## Adding as a dependency to your project

You'll need to add JCenter to your resolvers. In your `build.sbt`:
```scala
resolvers += Resolver.jcenterRepo
libraryDependencies += "com.iravid" %% "play-json-cats" % 0.2
```

`play-json-cats` is currently published only for Scala 2.11. Once `play-json` supports Scala 2.12, this package will be updated.

## Usage

```scala
import com.iravid.playjsoncats.implicits._
import play.api.libs.json._

case class Person(name: String, i: Int)

val rn: Reads[String] = Reads(j => (j \ "name").validate[String])
val ri: Reads[Int] = Reads(j => (j \ "i").validate[Int])
val rp: Reads[Person] = (rn |@| rp).map(Person)

val res: JsResult[Person] = rp.reads(Json.parse("""
  { "name": "iravid", "i": 42 }
"""))
```

## Provided instances

| `play-json` type | provided instances                            |
|------------------|-----------------------------------------------|
| `Reads[A]`       | `Functor`, `Applicative`, `Monad`             |
| `Writes[A]`      | `Contravariant`                               |
| `Format[A]`      | `Invariant`                                   |
| `JsResult[A]`    | `Functor`, `Applicative`, `ApplicativeError`  |
|                  | `Monad`, `MonadError`, `Traverse`,            |
|                  | `TraverseFilter`, `Monoid` (for `A: Monoid`), |
|   [![Build Status](https://travis-ci.org/iravid/play-json-cats.svg?branch=master)](https://travis-ci.org/iravid/play-json-cats)

# play-json-cats

`cats` typeclass instances for the `play-json` library.


