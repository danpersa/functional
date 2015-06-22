val numbers = Stream.cons(1, Stream.cons(2, Stream.empty))

val nums = Stream(1, 2, 3)

val moreNums = 0 #:: nums

println(moreNums)

moreNums.foreach(print)

moreNums.mkString(", ")

def rangeStream(from: Int, to: Int): Stream[Int] = {
  if (from > to) Stream.empty
  else Stream.cons(from, rangeStream(from + 1, to))
}


val range = rangeStream(3, 10)
range.mkString(", ")

def isEven(x: Int) = x % 2 == 0

val evenStream = range.filter(isEven)

evenStream.apply(0)

