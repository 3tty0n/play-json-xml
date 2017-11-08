package play.api.libs.json.implicits

import play.api.libs.json.{JsValue, Xml}

import scala.xml.NodeSeq

object JsonXmlImplicits {
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
