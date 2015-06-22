val numbers = List(1, 2, 3, 4)
val s = "Hello"

numbers zip s
numbers forall (e => e == 1)
numbers exists (e => e == 1)

s flatMap(c => c + ".")

numbers flatMap(List(1, _))
numbers sum