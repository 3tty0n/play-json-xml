package play.api.libs.json

import play.api.libs.json.Xml.toJson
import play.api.libs.json.Xml.toXml
import play.api.libs.json.implicits.JsonXmlImplicits._
import org.scalatest._
import play.api.libs.json._

import scala.xml.{Elem, NodeSeq}

class PlayJsonXmlSpec extends FlatSpec with Matchers {

  trait SetUp {
    val xml: Elem =
      <fruits>
        <fruit>
          <name>Aubergine</name>
          <name>Eggplant</name>
        </fruit>
        <fruit>
          <name>banana</name>
          <price>1000</price>
          <season>true</season>
          <delicious>true</delicious>
        </fruit>
        <fruit>
          <name colour="red">strawberry</name>
          <price>3000</price>
          <season>false</season>
          <delicious>true</delicious>
        </fruit>
        <fruit name="apple" price="500" seeds="yes">
          <season>false</season>
          <delicious>true</delicious>
        </fruit>
        <fruit price="5000" seeds="no" season="false" delicious="true">pineapple</fruit>
      </fruits>

    val xmlNodeSeq: NodeSeq = <fruits><fruit><name>Aubergine</name><name>Eggplant</name></fruit><fruit><name>banana</name><price>1000</price><season>true</season><delicious>true</delicious></fruit><fruit><name><value>strawberry</value><colour>red</colour></name><price>3000</price><season>false</season><delicious>true</delicious></fruit><fruit><name>apple</name><price>500</price><seeds>yes</seeds><season>false</season><delicious>true</delicious></fruit><fruit><value>pineapple</value><price>5000</price><seeds>no</seeds><season>false</season><delicious>true</delicious></fruit></fruits>
      .foldLeft(NodeSeq.Empty) { (a, b) => a ++ b }

    val json: JsValue = Json.parse(
      """
        |{
        |   "fruits":{
        |      "fruit":[
        |         {
        |           "name": [ "Aubergine", "Eggplant"]
        |         },
        |         {
        |            "name":"banana",
        |            "price":1000,
        |            "season":true,
        |            "delicious":true
        |         },
        |         {
        |            "name": {
        |               "value": "strawberry",
        |               "colour": "red"
        |            },
        |            "price":3000,
        |            "season":false,
        |            "delicious":true
        |         },
        |         {
        |            "name":"apple",
        |            "price":500,
        |            "seeds":"yes",
        |            "season":false,
        |            "delicious":true
        |         },
        |         {
        |            "value":"pineapple",
        |            "price":5000,
        |            "seeds":"no",
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
