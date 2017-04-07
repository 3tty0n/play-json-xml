package com.github.micchon.playjsonxml

import play.api.libs.json._
import scala.xml._

object JsonConverter {
  def toXml(json: JsValue): NodeSeq = {
    def toXml(name: String, json: JsValue): NodeSeq = json match {
      case JsObject(fields) =>
        val children = fields.toList.flatMap { case (n, v) => toXml(n, v) }
        new XmlNode(name, children)
      case JsArray(xs) =>
        xs.flatMap { v => toXml(name, v) }
      case JsNumber(v) =>
        new XmlElem(name, v.toString())
      case JsBoolean(v) =>
        new XmlElem(name, v.toString)
      case JsString(v) =>
        new XmlElem(name, v)
      case JsNull =>
        new XmlElem(name, "null")
    }

    json match {
      case JsObject(fields) =>
        fields.toList.flatMap { case (n, v) => toXml(n, v) }
      case x =>
        toXml("root", x)
    }
  }

  private class XmlNode(name: String, children: Seq[Node])
    extends Elem(null, name, xml.Null, TopScope, true, children :_*)

  private class XmlElem(name: String, value: String)
    extends Elem(null, name, xml.Null, TopScope, true, Text(value))

}
