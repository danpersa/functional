import scala.util.control.NonFatal

// a mondat has two operations
trait M[+T] {
  def flatMap[U](f: T => M[U]): M[U]

  def unit[T](x: T): M[T]

  def map[U](f: T => U): M[U] = flatMap(t => unit(f(t)))
}
val f: Int => Int = x => x * 2
val g: Int => Int = x => x + 5
(f andThen g)(3)

case class Present[T](int: T) extends IntOption[T]

object Absent extends IntOption[Nothing] {
  override def toString = "Absent"
}

class IntOption[+T] extends M[T] {
  override def flatMap[U](f: (T) => M[U]): M[U] = this match {
    case Present(x) => f(x)
    case Absent => Absent
  }

  override def unit[T](x: T): M[T] = Present(x)
}
val o1 = Present(1)
val o2 = Present(3)
val o3 = Present(4)
for {
  i1 <- o1
  i2 <- o2
} yield (i1 + i2)
o1.flatMap { i1 =>
  for {
    i2 <- o2
  } yield (i1 + i2)
}
o1.flatMap(x => o2.map(y => x + y))
val a1: IntOption[Int] = Absent
for {
  i1 <- o1
  i2 <- a1
} yield (i1 + i1)

o1.flatMap { i1 =>
  for {
    i2 <- a1
  } yield (i1 + i2)
}
o1.flatMap(i1 => a1.map(i2 => i1 + i2))
val f1: (Int => IntOption[Int]) = x => Present(x * 2)
val g1: (Int => IntOption[Int]) = x => Present(x + 5)
// associativity
(o1 flatMap f1 flatMap g1) == (o1 flatMap (x => f1(x).flatMap(g1)))
// left unit
o1.flatMap(f1) == f1(1)
// right unit
(o1 flatMap { i1 => Present(i1) }) == o1
case class Success[T](t: T) extends Try[T]
case class Failure(ex: Throwable) extends Try[Nothing]

sealed trait Try[+T] {
  def flatMap[U](f: T => Try[U]): Try[U] = this match {
    case Success(t) => f(t)
    case Failure(ex) => Failure(ex)
  }

  def unit[T](t: T) = Success(t)

  def map[U](f: T => U): Try[U] = this match {
    case Success(t) => Success(f(t))
    case Failure(ex) => Failure(ex)
  }
}


object Try {
  def apply[T](expr: => T): Try[T] = {
    try Success(expr)
    catch {
      case NonFatal(ex) => Failure(ex)
    }
  }
}

for {
  i <- Try(1 + 1)
  j <- Try(2 + 2)
} yield (i + j)

for {
  i <- Try(1 + 1)
  j <- Try(throw RuntimeException)
} yield (i + j)