package com.github.micchon.jsonxmlconverter

import play.api.libs.json._

import scala.util.{Failure, Success, Try}
import scala.xml._

object JsonXmlConverter {
  def toJson(node: Node): JsValue = {
    Json.toJson(
      node.child.map(toJsonXmlChildren).filter(_ != Json.obj())
    )
  }

  private def toJsonXmlChildren(node: Node): JsValue =
    if (node.child.size == 1) {
      Try(node.text.toInt) match {
        case Success(v) => JsNumber(v)
        case Failure(_) => Try(node.text.toBoolean) match {
          case Success(v) => JsBoolean(v)
          case Failure(_) => JsString(node.text)
        }
      }
    } else {
      JsObject(node.child.collect {
        case e: Elem => e.label -> toJsonXmlChildren(e)
      })
    }
}
