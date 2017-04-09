# play-json-xml [![Build Status](https://travis-ci.org/3tty0n/play-json-xml.svg?branch=master)](https://travis-ci.org/3tty0n/play-json-xml)

It converts between play-json and xml.


## Useage

If you want to convert xml to json,

```scala
import org.micchon.playjsonxml.XmlConverter.toJson
import org.micchon.playjsonxml.Implicits._
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

toJson(xml) == // or xml.toJson
  JsObject(Seq(
    "money" -> JsObject(Seq(
      "yen" -> JsObject(Seq("price" -> JsNumber(100))),
      "dol" -> JsObject(Seq("price" -> JsNumber(110)))
    ))
  ))
```

Or, if you want to convert json to xml,

```scala
import org.micchon.playjsonxml.JsonConverter.toXml
import org.micchon.playjsonxml.Implicits._
import play.api.libs.json._

val json = Json.parse(
  """
    |{
    |   "fruits":{
    |      "fruit":[
    |         {
    |            "name":"banana",
    |            "price":1000,
    |            "season":true,
    |            "delicious":true
    |         },
    |         {
    |            "name":"strowberry",
    |            "price":3000,
    |            "season":false,
    |            "delicious":true
    |         }
    |      ]
    |   }
    |}
  """.stripMargin)
  
toXml(json) == // or json.toXml
  <fruits><fruit><name>banana</name><price>1000</price><season>true</season><delicious>true</delicious></fruit><fruit><name>strowberry</name><price>3000</price><season>false</season><delicious>true</delicious></fruit></fruits>
```
