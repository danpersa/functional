

Option(1) flatMap { x =>
  println("got x")
  Option.empty[Int] flatMap { y =>
    println("got y")
    Option(3) map { z =>
      x + y + z
    }
  }
}

for {
  x <- Option(1)
  y <- Option.empty[Int]
  z <- Option(3)
} yield (x + y + z)