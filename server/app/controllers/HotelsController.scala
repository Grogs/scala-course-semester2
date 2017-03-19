package controllers

import javax.inject.{Inject, Singleton}

import autowire.Core.Request
import model.{Coordinates, Hotel}
import play.api.libs.json.Json
import upickle.{Js, json}
import upickle.default._
import play.api.mvc.{Action, Controller}
import services.hotels.HotelsService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class HotelsController @Inject()(hotelsService: HotelsService, webJarAssets: WebJarAssets) extends Controller {

  val googleMapsApiKey = Option(System.getProperty("GOOGLE_MAPS_API_KEY"))

  implicit val coordinates = Json.format[Coordinates]
  implicit val writes = Json.format[Hotel]

  object ApiServer extends autowire.Server[Js.Value, Reader, Writer] {
    def read[Result: Reader](p: Js.Value) = upickle.default.readJs[Result](p)
    def write[Result: Writer](r: Result) = upickle.default.writeJs(r)
  }

  def api(path: String) = Action.async{ implicit req =>

    val body = req.body.asText.getOrElse("")

    val parameters = json.read(body)
      .asInstanceOf[Js.Obj]
      .value
      .toMap

    val request = Request(path.split("/"), parameters)

    for {
      resp <- ApiServer.route[HotelsService](hotelsService)(request)
    } yield
      Ok(json.write(resp))
  }

  def search(destination: String, radius: String) = Action {

    val distance = radius.toDouble

    if (distance > 0) {
      Ok(
        views.html.searchResults(
          destination, radius,
          hotelsService.search(destination, distance)
        )(webJarAssets, googleMapsApiKey)
      )
    } else {
      BadRequest("Invalid distance")
    }
  }

}
