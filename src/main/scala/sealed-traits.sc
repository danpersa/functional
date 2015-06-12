
// can only be extended in the same file
sealed trait Optional[+A]

final case class Value[A](val value: A) extends Optional[A]

final object Absent extends Optional[Nothing]

// factory method design pattern
object Optional {
  def apply[A](value: A): Optional[A] = value match {
    case null => Absent
    case a => Value(a)
  }
}

// gets an warning, as the trait is sealed and we don't provide all of the options
(Optional(null)) match {
  case Absent => println("absent")
}

(Optional("100")) match {
  case Absent => println("absent")
  case Value(value) => println(s"value: $value")
}
