type GlassSize = Int
type GlassQuantity = Int

val glassSizes: Vector[GlassSize] = Vector(6, 9)

val requiredQuantity = 3

case class GlassQuantities(val glassQuantities: List[GlassQuantity]) {
  def apply(glassNumber: Int): GlassQuantity = glassQuantities(glassNumber)

  def updated(glassNumber: Int, newGlassQuantity: GlassQuantity) =
    GlassQuantities(glassQuantities.updated(glassNumber, newGlassQuantity))

  def isSolution(): Boolean = {
    !glassQuantities.filter(_ == requiredQuantity).isEmpty
  }
}

trait Op {
  def apply(glassQuantities: GlassQuantities): GlassQuantities
}

case class Empty(glassNumber: Int) extends Op {
  def apply(glassQuantities: GlassQuantities): GlassQuantities = glassQuantities.updated(glassNumber, 0)
}

case class Fill(glassNumber: Int) extends Op {
  def apply(glassQuantities: GlassQuantities): GlassQuantities = glassQuantities.updated(glassNumber, glassSizes(glassNumber))
}

case class Pour(fromGlassNumber: Int, toGlassNumber: Int) extends Op {
  def apply(glassQuantities: GlassQuantities): GlassQuantities = {
    val movedQuantity =
      if (glassQuantities(fromGlassNumber) + glassQuantities(toGlassNumber) <= glassSizes(toGlassNumber))
        glassQuantities(fromGlassNumber)
      else
        glassSizes(toGlassNumber) - glassQuantities(toGlassNumber)
    glassQuantities.updated(fromGlassNumber, glassQuantities(fromGlassNumber) - movedQuantity)
      .updated(toGlassNumber, glassQuantities(toGlassNumber) + movedQuantity)
  }
}

Pour(1, 0).apply(GlassQuantities(List(6, 3)))
Pour(1, 0).apply(GlassQuantities(List(1, 4)))
Pour(1, 0).apply(GlassQuantities(List(0, 1)))
Pour(1, 0).apply(GlassQuantities(List(6, 3)))
Pour(1, 0).apply(GlassQuantities(List(2, 9)))
Pour(0, 1).apply(GlassQuantities(List(2, 9)))
Pour(0, 1).apply(GlassQuantities(List(2, 7)))


val emptyOps = for {
  glassNumber <- (0 until glassSizes.size)

} yield Empty(glassNumber)

val fillOps = for {
  glassNumber <- (0 until glassSizes.size)
} yield Fill(glassNumber)

val pourOps = for {
  fromGlassNumber <- (0 until glassSizes.size)
  toGlassNumber <- (0 until glassSizes.size)
  if fromGlassNumber != toGlassNumber
} yield Pour(fromGlassNumber, toGlassNumber)

val operations = (emptyOps ++ fillOps ++ pourOps).toList

case class Path(path: List[Op]) {
  def apply(glassQuantities: GlassQuantities): GlassQuantities = path match {
    case Nil => throw new Error("Should Not Happen")
    case List(op) => op(glassQuantities)
    case op :: ops => {
      Path(ops).apply(op(glassQuantities))
    }
  }

  def isSolution(glassQuantities: GlassQuantities): Boolean = {
    val possibleSolution = apply(glassQuantities)
    possibleSolution.isSolution()
  }
}

def genPaths(paths: Set[Path]): Stream[Set[Path]] = {

  def genNextLevel(paths: Set[Path]): Set[Path] =
    for {
      path <- paths
      op <- operations
    } yield Path(op :: path.path)

  paths #:: genPaths(genNextLevel(paths))
}

val pathSetStream: Stream[Set[Path]] = genPaths(operations.map { op: Op => Path(List(op)) }.toSet)
val pathSets = pathSetStream.take(3).toList

for {
  pathSet <- pathSets
  path <- pathSet
  if path.isSolution(GlassQuantities(List(0, 0)))
} yield path
Path(List(Fill(1), Pour(1, 0), Pour(1, 0))).isSolution(GlassQuantities(List(0, 0)))