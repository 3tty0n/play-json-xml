package com.github.micchon.playjsonxml

import PlayJsonXml._
import org.scalatest._
import play.api.libs.json._

class PlayJsonXmlSpec extends FlatSpec with Matchers {

  "toXml" should "convert xml to json" in {
    val xml =
      <fruits>
        <fruit>
          <name>banana</name>
          <price>1000</price>
          <season>true</season>
          <delicious>true</delicious>
        </fruit>
        <fruit>
          <name>strowberry</name>
          <price>3000</price>
          <season>false</season>
          <delicious>true</delicious>
        </fruit>
      </fruits>

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
      """.stripMargin
    )

    toJson(xml) should equal(json)
  }

  it should "convert the xml its child size is zero" in {
    val xml =
      <zero></zero>

    val json = Json.parse(
      """
        |{"zero" : ""}
      """.stripMargin)

    val xml2 =
      <number id ="1">3</number>

    println(toJson(xml2))

    toJson(xml) should equal(json)
  }
}
