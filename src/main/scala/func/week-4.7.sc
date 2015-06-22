

val l = List(1, 2, 3)
l.head
l.tail
val fruits = "apples" :: "pears" :: "cherries" :: Nil
fruits.head
fruits.tail
fruits.last
fruits.init
fruits(2)
fruits take 2
fruits drop 2
def insert(x: Int, l: List[Int]): List[Int] = l match {
  case List() => List(x)
  case head :: tail if x <= head => x :: l
  case head :: tail if x > head => head :: insert(x, tail)
}

def isort(xs: List[Int]): List[Int] = xs match {
  case List() => List()
  case head :: tail => insert(head, isort(tail))
}

isort(List(3, 5, 1, 7))
val fr = List("apples") ++ List("pears", "cherries")
fr.reverse
fr updated (2, "strawberies")

