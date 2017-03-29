package com.github.micchon.jsonxmlconverter

import JsonXmlConverter._
import org.scalatest._
import play.api.libs.json._

class JsonXmlConverterSpec extends FlatSpec with Matchers {

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
      """[
        |  {
        |    "name":"banana",
        |    "price":1000,
        |    "season":true,
        |    "delicious":true
        |  },
        |  {
        |    "name":"strowberry",
        |    "price":3000,
        |    "season":false,
        |    "delicious":true
        |  }
        |]""".stripMargin
    )

   toJson(xml) should equal(json)
  }
}
