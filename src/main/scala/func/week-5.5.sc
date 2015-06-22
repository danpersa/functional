

List(1, 2, 4) reduceLeft(_ + _)
(List(1, 2, 4) foldLeft(0))(_ + _)
def reduceLeft[T](l: List[T])(f: (T, T) => T): T = l match {
  case Nil => throw new Error("Can't apply it to an empty list")
  case List(x) => x
  case x :: xs => reduceLeft(f(x, xs.head) :: xs.tail)(f)
}

reduceLeft(List(1, 2, 4))(_ + _)

def foldLeft[T](l: List[T])(unit: T, f: (T, T) => T): T = l match {
  case Nil => unit
  case List(x) => x
  case x :: xs => foldLeft(f(x, xs.head) :: xs.tail)(unit, f)
}

foldLeft(List(1, 2, 4))(0, _ + _)