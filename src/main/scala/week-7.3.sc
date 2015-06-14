

def from(start: Int): Stream[Int] = start #:: from(start + 1)
val from100 = from(100)
val even = from(0).map(_ * 2)
even.take(10).toList

def sieve(num: Stream[Int]): Stream[Int] = {
  num.head #:: sieve(num.tail.filter(_ % num.head != 0))
}

sieve(from(2)).take(100).toList




