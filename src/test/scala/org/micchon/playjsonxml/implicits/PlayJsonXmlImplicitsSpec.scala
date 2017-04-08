package org.micchon.playjsonxml.implicits

import PlayJsonXmlImplicits._
import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json.Json
import scala.xml.NodeSeq

class PlayJsonXmlImplicitsSpec extends FlatSpec with Matchers {

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
  }

  "xml.toJson" should "convert xml to play-json implicitly" in new SetUp {
    xml.toJson should equal(json)
  }

  "json.toXml" should "convert play-json to xml implicitly" in new SetUp {
    json.toXml should equal(xmlNodeSeq)
  }
}
