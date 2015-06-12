import demo.Hello

import scala.annotation.tailrec

var hello = new Hello
println(hello.hello("Dan"))
def constCallByName(x: Int, y: => Int) = 1
def constCallByValue(x: Int, y: Int) = 1
// println(constCallByName(10, loop))
// println(constCallByValue(10, loop))
// def is pass by name
// val is pass by value

def and(x: Boolean, y: => Boolean) = if (x) y else false
println(and(true, true))
println(and(false, false))
println(and(true, false))
println(and(false, true))
def or(x: Boolean, y: => Boolean) = if (x) true else if (y) true else false
println(or(true, true))
println(or(false, false))
println(or(true, false))
println(or(false, true))
// Coursera 1.5, 1.6
def sqrt(x: Double) = {
  def sqrtIter(m: Double): Double = {
    def isGoodEnough(m: Double): Boolean = {
      def abs(x: Double) = if (x < 0) -x else x
      abs(m * m - x) / x < 0.001
    }
    def improve(m: Double) = (m + x / m) / 2
    if (isGoodEnough(m)) m
    else {
      sqrtIter(improve(m))
    }
  }
  sqrtIter(1.0)
}
println("XXXX")
println(sqrt(2))

//Coursera 1.7
@tailrec
def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

println(gcd(14, 21))
def fact(n: Double): Double = {
  if (n == 0) 1 else n * fact(n - 1)
}
println(fact(3))
def fact1(n: Int): Int = {

  def loop(acc: Int, n: Int):Int = if (n == 0) acc else loop(acc * n, n - 1)
  loop(1, n)
}
println(fact1(3))


