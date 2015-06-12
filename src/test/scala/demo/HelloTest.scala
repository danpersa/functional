package demo

import org.scalatest.FunSuite

/**
 * @author dpersa
 */
class HelloTest extends FunSuite {

  test("hello should work") {
    val hello = new Hello
    assert(hello.hello("Scala") == "Hello Scala")
  }

}
