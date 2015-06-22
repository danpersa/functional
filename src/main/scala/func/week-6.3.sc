val fruit = Set("apple", "banna", "pear")

val s = (1 to 6).toSet


s map (_ / 2)

s contains 5

def rooks(n: Int): Set[List[Int]] = {
  def isSafe(col: Int, rooks: List[Int]): Boolean = rooks.size != (col :: rooks).toSet.size

  def placerooks(k: Int): Set[List[Int]] =
    if (k == 0) Set(List())
    else
      for {
        rooks <- placerooks(k - 1)
        col <- 0 until n
        if isSafe(col, rooks)
      } yield col :: rooks


  placerooks(n)
}



def show(rooks: List[Int]) = {
  val lines =
    for (col <- rooks.reverse) yield
      Vector.fill(rooks.length)("* ").updated(col, "X ").mkString
  "\n" + (lines mkString "\n")
}
(rooks(4) map show) mkString "\n"
