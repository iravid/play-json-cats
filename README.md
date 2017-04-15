[![Build Status](https://travis-ci.org/iravid/play-json-cats.svg?branch=master)](https://travis-ci.org/iravid/play-json-cats)

# play-json-cats

`cats` typeclass instances for the `play-json` library.

Usage example:
```scala
import com.iravid.playjsoncats.implicits._
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class Person(name: String, i: Int)

val rn: Reads[String] = (__ \ "name").read[String]
val ri: Reads[Int] = (__ \ "i").read[Int]
val rp: Reads[Person] = (rn |@| rp).map(Person)

val res: JsResult[Person] = rp.reads(Json.parse("""
  { "name": "iravid", "i": 42 }
"""))
```
