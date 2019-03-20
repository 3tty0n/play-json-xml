# play-json-xml

[![Build Status](https://travis-ci.org/3tty0n/play-json-xml.svg?branch=master)](https://travis-ci.org/3tty0n/play-json-xml) 
[![Maven Central](https://img.shields.io/maven-central/v/org.micchon/play-json-xml_2.12.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22org.micchon%22%20AND%20a:%22play-json-xml_2.12%22)


It converts between play-json and xml like json4s.

## Install

Builds are available for Scala 2.11.x and for 2.12.x. The main line of development of play-json-xml is 2.12.4.

```scala
libraryDependencies += "org.micchon" %% "play-json-xml" % "(version)"
```

## Useage

If you want to convert xml to json,

```scala
import play.api.libs.json.Xml
import play.api.libs.json.implicits._
import play.api.libs.json.Json

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

Xml.toJson(xml) == // or xml.toJson
  JsObject(Seq(
    "money" -> JsObject(Seq(
      "yen" -> JsObject(Seq("price" -> JsNumber(100))),
      "dol" -> JsObject(Seq("price" -> JsNumber(110)))
    ))
  ))
```

Or, if you want to convert json to xml,

```scala
import play.api.libs.json.Xml
import play.api.libs.json.implicits._
import play.api.libs.json.Json

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

Xml.toXml(json) == // or json.toXml
  <fruits><fruit><name>banana</name><price>1000</price><season>true</season><delicious>true</delicious></fruit><fruit><name>strowberry</name><price>3000</price><season>false</season><delicious>true</delicious></fruit></fruits>
```
