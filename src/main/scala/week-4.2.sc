
// : => call by name
abstract class Bool {
  def ifThenElse[T](t: => T, e: => T): T

  def &&(x: Bool) = ifThenElse(x, false)
  def ||(x: Bool) = ifThenElse(true, x)
}

object True extends Bool {
  override def ifThenElse[T](t: => T, e: => T): T = t
}

object False extends Bool {
  override def ifThenElse[T](t: => T, e: => T): T = e
}

abstract class Nat {
  def isZero: Boolean
  def predecesor: Nat
  def successor: Nat
  def + (that: Nat): Nat
  def - (that: Nat): Nat
}

object Zero extends Nat {
  override def isZero: Boolean = true

  override def successor: Nat = new Succ(this)

  override def predecesor: Nat = throw new IllegalStateException("Negative number detected")

  override def + (that: Nat): Nat = that

  override def - (that: Nat): Nat = throw new IllegalStateException("Negative number detected")


  override def toString = "0"
}

class Succ(n: Nat) extends Nat {
  override def isZero: Boolean = false

  override def successor: Nat = new Succ(this)

  override def predecesor: Nat = n

  override def + (that: Nat): Nat = this.predecesor + that.successor

  override def - (that: Nat): Nat = if (that.predecesor == Zero) this else this.predecesor - that.predecesor

  override def toString = {
    def toString(i: Int, nat: Nat): Int = if (nat == Zero) i else toString(i + 1, nat.predecesor)

    toString(0, this).toString
  }
}

object Succ {
  def apply(n: Nat) = new Succ(n)
}

val zero = Zero
val one = Succ(Zero)
val three = Succ(Succ(Succ(Zero)))

