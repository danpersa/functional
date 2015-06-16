package akka.streams

import akka.actor.ActorSystem
import akka.stream.ActorFlowMaterializer
import akka.stream.scaladsl.{Flow, Sink, Source, Tcp}
import akka.util.{ByteString, Timeout}

import scala.concurrent.Await
import scala.concurrent.duration._


/**
  * @author dpersa
  */
object AkkaStreamsTcp extends App {

   implicit val sys = ActorSystem("TheDemo")
   implicit val mat = ActorFlowMaterializer()
   implicit val timeout = Timeout(3000)

   val server = Tcp().bind("localhost", 0)
   .to(Sink.foreach { conn =>
     conn.flow.join(Flow[ByteString]).run()
   }).run()

   val myaddr = Await.result(server, 1.second)

   val client = Tcp().outgoingConnection(myaddr.localAddress)

   Source(0 to 10).map(x => ByteString(s"hello $x ")).via(client).map(_.decodeString("UTF-8")).runForeach(println)
 }
