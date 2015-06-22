def last[T](xs: List[T]): T = xs match {
  case List() => throw new Error("last of empty list")
  case List(x) => x
  case y :: ys => last(ys)
}

val fruits: List[String] = List("apples", "cherries", "grapes")
last(fruits)

def init[T](xs: List[T]): List[T] = xs match {
  case List() => throw new Error("init of empty list")
  case List(x) => List()
  case y :: ys => y :: init(ys)
}

init(fruits)

def concat[T](xs: List[T], ys: List[T]): List[T] = xs match {
  case List() => ys
  case z :: zs => z :: concat(zs, ys)
}

concat(fruits, fruits)

def reverse[T](xs: List[T]): List[T] = xs match {
  case List() => xs
  case z :: zs => reverse(zs) ++ List(z)
}

reverse(fruits)

def remove[T](n: Int, xs: List[T]): List[T] = xs match {
  case List() if n == 0  => List()
  case List() if n != 0 => throw new Error("Empty list, n != 0")
  case y :: ys if n == 0 => ys
  case y :: ys if n != 0 => y :: remove(n - 1, ys)
}

remove(1, fruits)

def remove1[T](n: Int, xs: List[T]) = (xs take n) ::: (xs drop n + 1)

remove(1, fruits)