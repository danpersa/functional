
val n = 10

(1 until n) flatMap { i =>
  (1 until i) map { j =>
    (i, j)
  }
}

for {
  i <- 1 until n
  j <- 1 until i
} yield (i, j)