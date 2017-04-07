package com.github.micchon.playjsonxml.implicits

import com.github.micchon.playjsonxml._
import play.api.libs.json._

import scala.xml._

object PlayJsonXmlImplicits {
  implicit class RichPlayJson(val xml: NodeSeq) {
    def toJson: JsValue = {
      XmlConverter.toJson(xml)
    }
  }

  implicit class RichXml(val json: JsValue) {
    def toXml: NodeSeq = {
      JsonConverter.toXml(json)
    }
  }
}
