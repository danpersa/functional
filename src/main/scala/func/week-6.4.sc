case class Book(title: String, authors: List[String])

val  books = List(
  Book(title = "First", authors = List("Ion Creanga", "Mihai Eminescu")),
  Book(title = "Second", authors = List("Calinescu", "Mihai Balanescu")),
  Book(title = "Third", authors = List("Petrescu", "Agarbiceanu")),
  Book(title = "Fourth", authors = List("Ion Creanga", "Mihail Sadoveanu")),
  Book(title = "Laast", authors = List("Ion Creanga", "Mihail Sadoveanu"))
)

for {
  book <- books
  author <- book.authors
  if author startsWith "Ion Creanga"
} yield book.title

books.flatMap { book =>
  for {
    author <- book.authors
    if author startsWith "Ion Creanga"
  } yield book.title
}

books.flatMap { book =>
  book.authors.withFilter(_ startsWith "Ion Creanga").map((author) => book.title)
}



