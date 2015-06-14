type Glass = Int
type State = Vector[Int]

val glassSizes: Vector[Glass] = Vector(6, 9)

val liters = 2

trait Op {
  // generates next state, after the op has been applied
  def apply(state: State): State
}

trait Path {
  def state: State

  override def toString = s"PathState: $state"

  override def hashCode(): Int = 34 * state.asInstanceOf[Int]

  override def equals(obj: scala.Any): Boolean = {
    if (!(obj.isInstanceOf[Path])) false
    else {
      this.state == obj.asInstanceOf[Path].state
    }
  }
}

case class RootPath(state: State) extends Path

case class NodePath(parent: Path, state: State) extends Path

case class Empty(glass: Glass) extends Op {
  def apply(state: State) =
    state.updated(glass, 0)
}

object Empty {

  def gen(path: Path): Set[Path] = {
    for {
      glass <- (0 until glassSizes.length)
    } yield (new Empty(glass))(path.state)
  }.toSet.map((state: State) => NodePath(path, state))
}

case class Fill(glass: Glass) extends Op {
  def apply(state: State) =
    state.updated(glass, glassSizes(glass))
}

object Fill {
  def gen(path: Path): Set[Path] = {
    for {
      glass <- (0 until glassSizes.length)
    } yield (new Fill(glass))(path.state)
  }.toSet.map((state: State) => NodePath(path, state))
}

case class Pour(from: Glass, to: Glass) extends Op {
  private def calcTo(state: State): Int =
    Math.min(glassSizes(to), state(to) + state(from))

  private def calcFrom(state: State): Int =
    if (state(to) + state(from) < glassSizes(to)) 0
    else state(to) + state(from) - glassSizes(to)

  def apply(state: State):State = {
    val newFrom = calcFrom(state)
    val newTo = calcTo(state)
    state.updated(from, newFrom).updated(to, newTo)
  }
}

object Pour {
  def gen(path: Path): Set[Path] = {
    for {
      from <- (0 until glassSizes.length)
      to <- (0 until glassSizes.length)
      if from != to
    } yield (new Pour(from, to))(path.state)
  }.toSet.map((state: State) => NodePath(path, state))
}

val initialState: State = Vector(0, 0)

def isSolution(path: Path): Boolean = path.state.find(_ == liters).isDefined



def genSolution(state: State): Set[Path] = {
  def genSolution(path: Path, discoveredPaths: Set[Path]): Set[Path] = {
    //println(s"possible path: $path")
    val possiblePaths = Fill.gen(path)  ++ Empty.gen(path) ++ Pour.gen(path)
    //println(s"possible paths: $possiblePaths")
    //println(s"discovered paths: $discoveredPaths")
    val solutions = possiblePaths.filter(isSolution)
    if (!solutions.isEmpty) {
      //println(s"solutions: $solutions")
      solutions
    }
    else {
      val notTraversedPaths = possiblePaths -- discoveredPaths
      //println(s"notTraversedPaths: $notTraversedPaths")
      for {
        possiblePath <- notTraversedPaths
      } yield genSolution(possiblePath, Set(path) ++ possiblePaths)
    }.flatten

  }

  genSolution(RootPath(initialState), Set.empty)
}

val paths = genSolution(initialState)
paths.foreach { path =>
  println(s"solution: ${path.state}")
}