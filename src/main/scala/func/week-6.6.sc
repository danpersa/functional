

val romans = Map("I" -> 1, "V" -> 5, "X" -> 10)
val capitalOfCountry = Map("US" -> "Washington", "Romania" -> "Bucharest")
capitalOfCountry("Romania")
// capitalOfCountry("Andorra")

capitalOfCountry get "Andorra"

trait Optional[+A]

case class Present[+A](value: A) extends Optional[A]

object Absent extends Optional[Nothing]

def showCapital(country: String) = capitalOfCountry.get(country) match {
  case Some(capital) => capital
  case None => "missing data"
}

showCapital("Andorra")
val fruit = List("banana", "grapes", "strawberries", "apples", "berries")
fruit sortWith ((a, b) => a < b)
fruit.sorted

fruit.groupBy(_.head)


class Poly(val terms: Map[Int, Double]) {
  def this(bindings: (Int, Double)*) = this(bindings.toMap)

  def +(other: Poly) = new Poly(terms ++ other.terms)

  override def toString =
    (for ((exp, coeff) <- terms.toList.sorted.reverse) yield coeff + "x^" + exp) mkString " + "
}


val p1 = new Poly(Map(1 -> 2.0, 3 -> 4.0, 5 -> 6.2))
val p2 = new Poly(0 -> 3.0, 3 -> 7.0)
p1 + p2

val cap1 = capitalOfCountry withDefaultValue "unknown"