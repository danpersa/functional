
// don't use this one in scala :)
final case class FavoriteNumbersJavaWay(l: List[Int]) {
  require(l.nonEmpty)
}

final case class MyFavoriteNumbers(val l: List[Int])

object FavoriteNumbers {

  def apply(l: List[Int]): Either[String, MyFavoriteNumbers] = l match {
    case head :: tail => new Right(MyFavoriteNumbers(l))
    case Nil => new Left("Need at least 1 favorite number")
  }
}

val failure: String => Int = _.length

val success: MyFavoriteNumbers => Int = _.l.head
val favoriteNumbers = FavoriteNumbers(List(1, 2, 3))
favoriteNumbers.fold(failure, success)


val favoriteNumbersEmpty = FavoriteNumbers(List())
favoriteNumbersEmpty.fold(failure, success)