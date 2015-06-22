lazy val x = "Hello"

val expr = {
  val x = {
    print("x"); 1
  }
  lazy val y = {
    print("y"); 2
  }
  def z = {
    print("z"); 3
  }

  z + x + y + x + z
}



