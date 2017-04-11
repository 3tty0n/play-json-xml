package org.micchon.playjsonxml

import play.api.libs.json.JsValue
import scala.xml.NodeSeq

object Implicits {
  implicit class RichPlayJson(val xml: NodeSeq) {
    def toJson: JsValue = {
      Xml.toJson(xml)
    }
  }

  implicit class RichXml(val json: JsValue) {
    def toXml: NodeSeq = {
      Xml.toXml(json)
    }
  }
}
