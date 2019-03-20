package play.api.libs.json

import play.api.libs.json.Xml.toJson
import play.api.libs.json.Xml.toXml
import play.api.libs.json.implicits._
import org.scalatest._
import play.api.libs.json._

import scala.xml.{Elem, NodeSeq}

class PlayJsonXmlSpec extends FlatSpec with Matchers {

  trait SetUp {
    val fruitXml: Elem =
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

    val fruitXmlNodeSeq: NodeSeq = <fruits><fruit><name>Aubergine</name><name>Eggplant</name></fruit><fruit><name>banana</name><price>1000</price><season>true</season><delicious>true</delicious></fruit><fruit><name><value>strawberry</value><colour>red</colour></name><price>3000</price><season>false</season><delicious>true</delicious></fruit><fruit><name>apple</name><price>500</price><seeds>yes</seeds><season>false</season><delicious>true</delicious></fruit><fruit><value>pineapple</value><price>5000</price><seeds>no</seeds><season>false</season><delicious>true</delicious></fruit></fruits>
      .foldLeft(NodeSeq.Empty) { (a, b) => a ++ b }

    val fruitJson: JsValue = Json.parse(
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

    val vegetableXml: Elem =
      <vegetables>
        <vegetable>artichoke</vegetable>
        <vegetable>broccoli</vegetable>
        <vegetable>carrot</vegetable>
        <not_a_vegetable>
          <fruit>tomato</fruit>
          <spice>salt</spice>
          <spice>pepper</spice>
        </not_a_vegetable>
      </vegetables>

    val vegetableXmlNodeSeq: NodeSeq = <vegetables><vegetable>artichoke</vegetable><vegetable>broccoli</vegetable><vegetable>carrot</vegetable><not_a_vegetable><fruit>tomato</fruit><spice>salt</spice><spice>pepper</spice></not_a_vegetable></vegetables>
      .foldLeft(NodeSeq.Empty) { (a, b) => a ++ b }

    val vegetableJson: JsValue = Json.parse(
      """
        |{
        |   "vegetables":{
        |      "vegetable":[
        |         "artichoke",
        |         "broccoli",
        |         "carrot"
        |      ],
        |      "not_a_vegetable": {
        |          "fruit": "tomato",
        |          "spice": [
        |             "salt",
        |             "pepper"
        |          ]
        |      }
        |   }
        |}
      """.stripMargin
    )
  }

  "toJson" should "convert fruit xml to json" in new SetUp {
    toJson(fruitXml) should equal(fruitJson)
  }

  "xml.toJson" should "convert fruit xml to json implicitly" in new SetUp {
    fruitXml.toJson should equal(fruitJson)
  }

  "toXml" should "convert fruit json to xml" in new SetUp {
    toXml(fruitJson) should equal(fruitXmlNodeSeq)
  }

  "json.toXml" should "convert fruit json to xml implicitly" in new SetUp {
    fruitJson.toXml should equal(fruitXmlNodeSeq)
  }

  "toJson" should "convert vegetable xml to json" in new SetUp {
    toJson(vegetableXml) should equal(vegetableJson)
  }

  "xml.toJson" should "convert vegetable xml to json implicitly" in new SetUp {
    vegetableXml.toJson should equal(vegetableJson)
  }

  "toXml" should "convert vegetable json to xml" in new SetUp {
    toXml(vegetableJson) should equal(vegetableXmlNodeSeq)
  }

  "json.toXml" should "convert vegetable json to xml implicitly" in new SetUp {
    vegetableJson.toXml should equal(vegetableXmlNodeSeq)
  }
}
