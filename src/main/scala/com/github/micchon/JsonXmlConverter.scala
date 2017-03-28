package com.github.micchon

import scala.xml._
import play.api.libs.json._

object JsonXmlConverter {
  def toJson(node: Node): JsValue = {
    Json.toJson(
      node.child.map(toJsonXmlChildren).filter(_ != Json.obj())
    )
  }

  private def toJsonXmlChildren(node: Node): JsValue =
    if (node.child.size == 1) {
      JsString(node.text)
    } else {
      JsObject(node.child.collect {
        case e: Elem if e.nonEmpty =>
          e.label -> toJsonXmlChildren(e)
      })
    }
}
