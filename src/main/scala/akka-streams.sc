import akka.actor.ActorDSL._
import akka.actor.ActorSystem
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.Source
import akka.util.Timeout
implicit val sys = ActorSystem("TheDemo")
implicit val mat = ActorFlowMaterializer()
implicit val timeout = Timeout(3000)
import sys.dispatcher
val numbers = Source(List(1, 2, 4))
val strings = Source(List("a", "b", "c"))
strings.runForeach(println)

//  val composite = Source() { implicit b =>
//    val zip = b.add(Zip[Int, String]())
//    zip.in0 <~ numbers
//    strings ~> zip.in1
//    zip.out
//  }

val fast = Source(() => Iterator from 0)

fast.runForeach(println)

fast.map(x => {
  Thread.sleep(1000);
  x
}).runForeach(println)
