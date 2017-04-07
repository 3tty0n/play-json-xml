package com.github.micchon.playjsonxml.implicits

import com.github.micchon.playjsonxml.PlayJsonXml
import play.api.libs.json._

import scala.xml._

object PlayJsonXmlImplicits {
  implicit class RichPlayJson(val xml: NodeSeq) {
    def toJson: JsValue = {
      PlayJsonXml.toJson(xml)
    }
  }
}
