package org.micchon.playjsonxml

import org.micchon.playjsonxml.JsonConverter.toXml
import org.micchon.playjsonxml.XmlConverter.toJson
import org.micchon.playjsonxml.Implicits._
import org.scalatest._
import play.api.libs.json._
import scala.xml.NodeSeq

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

  }

  "toJson" should "convert xml to json" in new SetUp {
    toJson(xml) should equal(json)
  }

  "xml.toJson" should "convert xml to json implicitly" in new SetUp {
    xml.toJson should equal(json)
  }

  "toXml" should "convert json to xml" in new SetUp {
    toXml(json) should equal(xmlNodeSeq)
  }

  "json.toXml" should "convert json to xml implicitly" in new SetUp {
    json.toXml should equal(xmlNodeSeq)
  }
}
