import java.util.Random

trait Generator[+T] {
  self =>
  def generate: T


  def map[G](f: T => G): Generator[G] = new Generator[G] {
    override def generate: G = f(self.generate)
  }

  def flatMap[G](f: T => Generator[G]): Generator[G] = new Generator[G] {
    override def generate: G = f(self.generate).generate
  }
}

val integers = new Generator[Int] {
  val rand = new Random

  def generate = rand.nextInt()
}

val booleans = new Generator[Boolean] {
  def generate = integers.generate > 0
}

val pairs = new Generator[(Int, Int)] {
  def generate = (integers.generate, integers.generate)
}

def booleans1 = for (x <- integers) yield (x > 0)

def booleans2 = integers.map(_ < 0)

booleans2.generate

def pairs1 = {
  for {
    first <- integers
    second <- integers
  } yield (first, second)
}

pairs1.generate

def single[T](x: T) = new Generator[T] {
  def generate = x
}

def emptyLists = for {
  emptyList <- single(List())
} yield emptyList

def nonEmptyLists: Generator[List[Int]] = for {
  head <- integers
  tail <- lists
} yield (head :: tail)

def lists: Generator[List[Int]] = {
  for {
    isEmpty <- booleans2
    list <- if (isEmpty) emptyLists else nonEmptyLists
  } yield list
}

lists.generate

