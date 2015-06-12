
implicit val compInt = (x: Int, y: Int) => x < y
implicit val compString = (x: String, y: String) => x.compareTo(y) < 0
def msort[T](xs: List[T])(implicit lt: (T, T) => Boolean): List[T] = {
  val n = xs.length / 2
  if (n == 0) xs
  else {
    def merge(xs: List[T], ys: List[T]): List[T] = (xs, ys) match {
      case (List(), ys) => ys
      case (xs, List()) => xs
      case (x :: xt, y :: yt) =>
        if (lt(x, y)) x :: merge(xt, ys)
        else y :: merge(xs, yt)
    }
    val (fst, snd) = xs splitAt n
    merge(msort(fst), msort(snd))
  }
}
val nums = List(2, -4, 5, 7, 1)
msort(nums)
val fruits = List("pinaple", "orange", "grape", "apple")
msort(fruits)