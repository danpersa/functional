trait List[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
}

class Cons[+T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty = false
}

object Cons {
  def apply[T](head: T, tail: List[T]) = new Cons[T](head, tail)
}

object Nil extends List[Nothing] {
  def isEmpty = true
  def head = throw new NoSuchElementException("Nil.head")
  def tail = throw new NullPointerException("Nil.tail")
}

object nth {
  def nth[T](n: Int, xs: List[T]): T =
    if (n == 0) xs.head
    else nth(n - 1, xs.tail)
}

object List {
  def apply[T](x1: T, x2: T) = Cons(x1, Cons(x2, Nil))
}

nth.nth(3, Cons(1, Cons(2, Cons(3, Cons(4, Nil)))))