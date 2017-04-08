# play-json-xml [![Build Status](https://travis-ci.org/3tty0n/play-json-xml.svg?branch=master)](https://travis-ci.org/3tty0n/play-json-xml)

It converts between play-json and xml.


## Useage

```scala
import org.micchon.playjsonxml.XmlConverter.toJson
import play.api.libs.json._

import scala.xml._

val xml =
  <money>
    <yen>
      <price>100</price>
    </yen>
    <dol>
      <price>110</price>
    </dol>
  </money>

val json = JsObject(Seq(
  "money" -> JsObject(Seq(
    "yen" -> JsObject(Seq("price" -> JsNumber(100))),
    "dol" -> JsObject(Seq("price" -> JsNumber(110)))
  ))
))

toJson(xml) // => json

import org.micchon.playjsonxml.implicits._

xml.toJson // => json
```
