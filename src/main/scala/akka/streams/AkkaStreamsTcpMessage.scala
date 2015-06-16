package akka.streams

import java.nio.ByteOrder

import akka.actor.ActorSystem
import akka.stream.{BidiShape, ActorFlowMaterializer}
import akka.stream.scaladsl.{Flow, BidiFlow}
import akka.util.{ByteString, Timeout}

/**
 * @author dpersa
 */
class AkkaStreamsTcpMessage extends App {

  implicit val sys = ActorSystem("TheDemo")
  implicit val mat = ActorFlowMaterializer()
  implicit val timeout = Timeout(3000)

  trait Message
  case class Ping(id: Int) extends Message
  case class Pong(id: Int) extends Message

  def toBytes(msg: Message): ByteString = {
    implicit val order = ByteOrder.LITTLE_ENDIAN
    msg match {
      case Ping(id) => ByteString.newBuilder.putByte(1).putInt(id).result()
      case Pong(id) => ByteString.newBuilder.putByte(2).putInt(id).result()
    }
  }

  def fromBytes(bytes: ByteString): Message = {
    implicit val order = ByteOrder.LITTLE_ENDIAN
    val it = bytes.iterator
    it.getByte match  {
      case 1 => Ping(it.getInt)
      case 2 => Pong(it.getInt)
      case other => throw new RuntimeException(s"parse error: expected 1/2, got $other")
    }
  }

  val codec = BidiFlow() { implicit b =>
    val top = b.add(Flow[Message].map(toBytes))
    val bottom = b.add(Flow[ByteString].map(fromBytes))
    BidiShape(top, bottom)
  }

}


