package org.micchon.playjsonxml

import play.api.libs.json._

import scala.util.{Failure, Success, Try}
import scala.xml._

object XmlConverter {
  def toJson(xml: NodeSeq): JsValue = {

    def isEmpty(node: Node): Boolean = node.child.isEmpty

    def isLeaf(node: Node) = {
      def descendant(n: Node): List[Node] = n match {
        case g: Group => g.nodes.toList.flatMap(x => x :: descendant(x))
        case _ => n.child.toList.flatMap { x => x :: descendant(x) }
      }

      !descendant(node).exists(_.isInstanceOf[Elem])
    }

    def isArray(nodeNames: Seq[String]) = nodeNames.size != 1 && nodeNames.toList.distinct.size == 1

    def directChildren(n: Node): NodeSeq = n.child.filter(c => c.isInstanceOf[Elem])

    def nameOf(n: Node) = (if (n.prefix ne null) n.prefix + ":" else "") + n.label

    def buildAttrs(n: Node) = n.attributes.map((a: MetaData) => (a.key, XValue(a.value.text))).toList


    sealed abstract class XElem extends Product with Serializable
    case class XValue(value: String) extends XElem
    case class XLeaf(value: (String, XElem), attrs: List[(String, XValue)]) extends XElem
    case class XNode(fields: List[(String, XElem)]) extends XElem
    case class XArray(elems: List[XElem]) extends XElem

    def toJsValue(x: XElem): JsValue = x match {
      case x@XValue(_) => xValueToJsValue(x)
      case XLeaf((name, value), attrs) => (value, attrs) match {
        case (_, Nil) => toJsValue(value)
        case (XValue(""), xs) => JsObject(mkFields(xs))
        case (_, _) => JsObject(Seq(name -> toJsValue(value)))
      }
      case XNode(xs) => JsObject(mkFields(xs))
      case XArray(elems) => JsArray(elems.map(toJsValue))
    }

    def xValueToJsValue(xValue: XValue): JsValue = {
      (Try(xValue.value.toInt), Try(xValue.value.toBoolean)) match {
        case (Success(v), Failure(_)) => JsNumber(v)
        case (Failure(_), Success(v)) => JsBoolean(v)
        case _ => JsString(xValue.value)
      }
    }

    def mkFields(xs: List[(String, XElem)]): List[(String, JsValue)] =
      xs.flatMap { case (name, value) => (value, toJsValue(value)) match {
        case (XLeaf(_, _ :: _), o: JsObject) => o.fields
        case (_, json) => Seq(name -> json)
      }}

    def buildNodes(xml: NodeSeq): List[XElem] = xml match {
      case n: Node =>
        if (isEmpty(n)) XLeaf((nameOf(n), XValue("")), buildAttrs(n)) :: Nil
        else if (isLeaf(n)) XLeaf((nameOf(n), XValue(n.text)), buildAttrs(n)) :: Nil
        else {
          val children = directChildren(n)
          XNode(buildAttrs(n) ::: children.map(nameOf).toList.zip(buildNodes(children))) :: Nil
        }
      case nodes: NodeSeq =>
        val allLabels = nodes.map(_.label)
        if (isArray(allLabels)) {
          val arr = XArray(nodes.toList.flatMap { n =>
            if (isLeaf(n) && n.attributes.length == 0) XValue(n.text) :: Nil
            else buildNodes(n)
          })
          XLeaf((allLabels.head, arr), Nil) :: Nil
        } else nodes.toList.flatMap(buildNodes)
    }

    buildNodes(xml) match {
      case List(x@XLeaf(_, _ :: _)) => toJsValue(x)
      case List(x) => play.api.libs.json.Json.obj(nameOf(xml.head) -> toJsValue(x))
      case x => JsArray(x.map(toJsValue))
    }

  }
}
