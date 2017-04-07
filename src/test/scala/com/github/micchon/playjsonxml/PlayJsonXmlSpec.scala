package com.github.micchon.playjsonxml

import PlayJsonXml._
import org.scalatest._
import play.api.libs.json._
import scala.xml.{Elem, NodeSeq}

class PlayJsonXmlSpec extends FlatSpec with Matchers {

  trait SetUp {
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

    val xmlNodeSeq: NodeSeq =
      <fruits><fruit><name>banana</name><price>1000</price><season>true</season><delicious>true</delicious></fruit><fruit><name>strowberry</name><price>3000</price><season>false</season><delicious>true</delicious></fruit></fruits>
      .foldLeft(NodeSeq.Empty){ (a, b) => a ++ b }

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

    val xml2 =
      <zero></zero>

    val json2 = Json.parse(
      """
        |{"zero" : ""}
      """.stripMargin)

  }

  "toJson" should "convert xml to json" in new SetUp {
    toJson(xml) should equal(json)
  }

  it should "convert the xml its child size is zero" in new SetUp {
    toJson(xml2) should equal(json2)
  }

  "toXml" should "convert json to xml" in new SetUp {
    toXml(json) should equal(xmlNodeSeq)
  }
}
