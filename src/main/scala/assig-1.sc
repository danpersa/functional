
def pascal(c: Int, r: Int): Int = {
 if (c == 0 || r == 0 || r == c) 1 else pascal(c - 1, r - 1) + pascal(c, r - 1)
}

println(pascal(0, 0))
println(pascal(1, 1))
println(pascal(1, 2))
println(pascal(1, 3))
println(pascal(3, 4))
println(pascal(3, 3))
println(pascal(2, 4))
def balance(chars: List[Char]): Boolean = {
  def bal(chars: List[Char], n: Int): Boolean = {
    if (n < 0) return false
    chars match {
      case Nil => n == 0
      case '(' :: tail => bal(tail, n + 1)
      case ')' :: tail => bal(tail, n - 1)
      case x :: tail => bal(tail, n)
    }
  }
  bal(chars, 0)
}

println(balance("(if (zero? x) max (/ 1 x))".toList))
println(balance("I told him (that it's not (yet) done).\n(But he wasn't listening)".toList))
println(balance(":-)".toList))
println(balance("())(".toList))

def perm(l: List[Int]): List[List[Int]] = l match {
  case Nil => List(Nil)
  case List(a) => List(l)
  case _ =>
    (for (i <- l.indices.toList) yield {
      val (before, rest) = l.splitAt(i)
      val elem = rest.head
      val subpermutes = perm(before ++ rest.tail)
      subpermutes.map(elem :: _)
    }).flatten
}
println(perm(List(1, 2, 3, 4)))

def countChange(money: Int, coins: List[Int]): Int = {

  if (money == 0) return 0

  def count(moneyLeft: Int, coinsLeft: List[Int], n: Int): Int = {
    //println(s"Call count: $moneyLeft, $coinsLeft, $n")
    if (moneyLeft < 0) n
    else if (moneyLeft == 0) n + 1
    else coinsLeft match {
      case Nil => 0
      case List(coin) => if (moneyLeft % coin == 0) 1 else 0
      case _ => {
        (for (j <- 0 until coinsLeft.length) yield {
          val (before, rest) = coinsLeft.splitAt(j)
          val elem = rest.head
          var number = 0
          //println("moneyLeft / head: " + moneyLeft / elem)
          for (i <- 0 to moneyLeft / elem) yield {
            number += count(moneyLeft - i * elem, before ++ rest.tail, n)
            number
          }
          n + number
        }).foldRight(0)(_ + _)
      }
    }
  }

  count(money, coins, 0) / 2
}


println(countChange(4, List(1, 2)))
println(countChange(0, List(1, 2)))
println(countChange(1, List(1, 2)))
println(countChange(4, List(1, 2, 3)))