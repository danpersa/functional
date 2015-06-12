val nums = List(1, -3, 2, -1, 3, 4, -5)

def squareList(xs: List[Int]): List[Int] = xs match {
  case Nil => Nil
  case y :: ys => y * y :: squareList(ys)
}


squareList(nums)

def squareList1(xs: List[Int]): List[Int] = xs.map(x => x * x)
squareList1(nums)
nums filter (x => x > 0)
nums partition (x => x > 0)
nums takeWhile (x => x > 0)
def pack[T](xs: List[T]): List[List[T]] = xs match {
  case Nil => Nil
  case y :: ys => List(y :: ys.takeWhile(p => p == y)) ::: pack(ys.dropWhile(p => p == y))
}

val letters: List[String] = List("a", "a", "a", "b", "c", "c", "c", "a")
pack(letters)

def encode[T](xs: List[T]): List[(T, Int)] = pack(xs).map(l => (l.head, l.length))

encode(letters)
