def merge(xs: List[Int], ys: List[Int]): List[Int] = (xs, ys) match {
  case (List(), ys) => ys
  case (xs, List()) => xs
  case (x :: xt, y :: yt) if (x <= y) => x :: merge(xt, ys)
  case (x :: xt, y :: yt) if (x > y) => y :: merge(xs, yt)
}


def msort(xs: List[Int]): List[Int] = {
  val n = xs.length / 2
  if (n == 0) xs
  else {
    val (fst, snd) = xs splitAt n
    merge(msort(fst), msort(snd))
  }
}

val nums = List(2, -4, 5, 7, 1)

msort(nums)