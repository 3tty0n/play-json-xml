package play.api.libs.json

import scala.xml.NodeSeq

package object implicits {

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
