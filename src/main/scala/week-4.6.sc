trait Expr {
  def eval: Int = this match {
    case Number(x) => x
    case Sum(e1, e2) => e1.eval + e2.eval
  }
}

case class Number(x: Int) extends Expr

case class Sum(e1: Expr, e2: Expr) extends Expr

object Eval {

  def eval(e: Expr): Int = e match {
    case Number(x) => x
    case Sum(e1, e2) => eval(e1) + eval(e2)
  }
}

Eval.eval(Sum(Number(1), Number(10)))

Sum(Number(1), Number(10)).eval

