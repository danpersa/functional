

abstract class Json

case class JSeq(elems: List[Json]) extends Json

case class JObj(bindings: Map[String, Json]) extends Json

case class JNum(num: Double) extends Json

case class JStr(str: String) extends Json

case class JBool(b: Boolean) extends Json

case object JNull extends Json

def show(json: Json): String = json match {
  case JSeq(elems) => "[" + (elems map show mkString ", ") + "]"
  case JObj(bindings) => val assoc = bindings map {
    case (key, value) => "\"" + key + "\": " + show(value)
  }
    "{" + (assoc mkString ", ") + "}"
  case JNum(num) => num.toString
  case JStr(str) => '\"' + str + '\"'
  case JBool(b) => b.toString
  case JNull => "null"
}
val f: String => String = {
  case "ping" => "pong"
}
f("ping")
// f("pong")
val pf: PartialFunction[String, String] = {
  case "ping" => "pong"
}
pf.isDefinedAt("ping")
pf.isDefinedAt("pong")
val data: List[Json] = List.empty
for {
  JObj(bindings) <- data
  JSeq(phones) = bindings("phoneNumbers")
  JObj(phone) <- phones
  JStr(digits) = phone("number")
  if digits startsWith "212"
} yield (bindings("firstName"), bindings("lastName"))