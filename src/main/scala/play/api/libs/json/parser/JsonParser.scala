package play.api.libs.json.parser

import scala.util.{Try, Success => S, Failure => F}
import scala.util.parsing.combinator._

class JSON extends JavaTokenParsers {

  def value: Parser[Any] = (
    obj
  | arr
  | stringLiteral
  | floatingPointNumber ^^ (_.toDouble)
  | "null" ^^ (_ => null)
  | "true" ^^ (_ => true)
  | "false" ^^ (_ => false)
  )

  def obj: Parser[Map[String, Any]] = "{" ~> repsep(member, ",") <~ "}" ^^ (Map() ++ _)

  def arr: Parser[List[Any]] = "[" ~> repsep(value, ",") <~ "]"

  def member: Parser[(String, Any)] = stringLiteral ~ ":" ~ value ^^ {
    case name ~ ":" ~ value => (name, value)
  }

  def parseString(input: String): Try[Any] = parseAll(value, input) match {
    case Success(result, next) => S(result)
    case Failure(msg, next) => F(new RuntimeException(msg))
  }

}

object JsonParser extends JSON {

  def main(args: Array[String]): Unit = {
    val json =
      """
        |{
        |      "address book": {
        |        "name": "John Smith",
        |        "address": {
        |          "street": "10 Market Street",
        |          "city"  : "San Francisco, CA",
        |          "zip"   : 94111
        |        },
        |        "phone numbers": [
        |          "408 338-4238",
        |          "408 111-6892"
        |        ]
        |      }
        |    }
      """.stripMargin

    parseString(json) match {
      case S(msg) => println(msg)
      case F(e) => throw e
    }
  }
}
