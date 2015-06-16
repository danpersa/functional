package akka.streams

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.server.Directives._
import akka.stream.{OperationAttributes, ActorFlowMaterializer}
import akka.stream.scaladsl.{Flow, Sink}
import akka.util.{ByteString, Timeout}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import akka.pattern.after
import scala.concurrent.duration._

/**
 * @author dpersa
 */
object AkkaStreamsHttp extends App {

  implicit val sys = ActorSystem("TheDemo")
  implicit val mat = ActorFlowMaterializer()
  implicit val timeout = Timeout(3000)

  val f = Flow[ByteString].mapAsync(4)(x =>
    after(1.second, sys.scheduler)(Future.successful(x))
  )

  val route = pathPrefix("demo") {
    getFromBrowseableDirectories("/Users")
  } ~ path("upload") {
    extractRequest { req =>
      req.entity.dataBytes.via(f).to(Sink.ignore).run()
      complete(StatusCodes.OK)
    }
  }

  Http().bind("localhost", 8080).runForeach(conn => conn.flow.join(route).run())
}
