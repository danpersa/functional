package akka.streams

import akka.actor.ActorSystem
import akka.stream.{OperationAttributes, ActorFlowMaterializer}
import akka.stream.scaladsl.{Flow, Sink, Source, Tcp}
import akka.util.{ByteString, Timeout}
import akka.pattern.after

import scala.concurrent.{Future, Await}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


/**
 * @author dpersa
 */
object AkkaStreamsDemo extends App {

  implicit val sys = ActorSystem("TheDemo")
  implicit val mat = ActorFlowMaterializer()
  implicit val timeout = Timeout(3000)

  println("hello world")
  val fast = Source(() => Iterator from 0)

  //  fast.map(x => {
  //    Thread.sleep(1000);
  //    x
  //  }).runForeach(println)

  println("hello world end")

  val flow = Flow[Int].withAttributes(OperationAttributes.inputBuffer(1, 1))

  fast.mapAsync(4)(x =>
    after(1.second, sys.scheduler)(Future.successful(x))
  ).via(flow)
    .runForeach(println)


}
