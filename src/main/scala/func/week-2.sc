def sum(f: Int => Int, a: Int, b: Int): Int = {
  if (a > b) 0
  else f(a) + sum(f, a + 1, b)
}


def sumOfCubes(a: Int, b: Int) = sum((a) => a * a, a, b)

def sum1(f: Int => Int, a: Int, b: Int): Int = {

  def loop(a: Int, acc: Int): Int = {
    println(s"$a $acc")
    if (a > b) acc
    else loop(a + 1, f(a) + acc)
  }

  loop(a, 0)
}
println(sum1(x => x * x, 2, 5))
def sum2(f: Int => Int): (Int, Int) => Int = {
  def sumF(a: Int, b: Int): Int =
    if (a > b) 0
    else f(a) + sumF(a + 1, b)
  sumF
}

def sumOfCubes1 = sum2(x => x * x * x)

def sum3(f: Int => Int)(a: Int, b: Int): Int =
  if (a > b) 0 else f(a) + sum3(f)(a + 1, b)

def product(f: Int => Int)(a: Int, b: Int): Int =
  if (a > b) 1 else f(a) * product(f)(a + 1, b)

def prodOrSum(g: Int => Int)(a: Int, b: Int)(sum: (Int, Int) => Int, neutral: Int): Int =
  if (a > b) neutral
  else sum(g(a), prodOrSum(g)(a + 1, b)(sum, neutral))

prodOrSum(x => x * x)(3, 4)((q, w) => q * w, 1)


class Rational(x: Int, y: Int) {
  require(y != 0, "denominator should not be zero")

  def this(x: Int) = this(x, 1)

  private def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)
  private val g = gcd(x, y)
  def numer = x / g
  def denom = y / g

  def + (that: Rational) =
    new Rational(numer * that.denom + that.numer + denom, denom * that.denom)

  def unary_- = new Rational(-numer, denom)

  def - (that: Rational) = this + -that

  def < (that: Rational) = numer * that.denom < that.numer * denom

  def max(that: Rational) = if (this < that) that else this

}



