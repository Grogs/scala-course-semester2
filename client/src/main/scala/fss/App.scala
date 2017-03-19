package fss

import autowire._
import google.maps.InfoWindowOptions
import model.{Coordinates, Hotel}
import org.scalajs.dom.html.{Button, Input}
import org.scalajs.dom.{Element, Event, document}
import services.hotels.HotelsService
import views.HotelListingTable

import scalatags.Text.all._

import org.scalajs.jquery.{JQueryEventObject, jQuery}


import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport

@JSExport
class App extends JSApp {

  def hotelsTables() = document.querySelector("table")
  def destination() = document.getElementById("destination").asInstanceOf[Input]
  def distance() = document.getElementById("distance").asInstanceOf[Input]
  def searchButton() = document.getElementById("load-hotels").asInstanceOf[Button]


  @JSExport
  def main(): Unit = {


    new Autocomplete(
      document.getElementById("destination").asInstanceOf[Input],
      Seq("London", "Paris", "Bath"),
      _ => handleChange(null)
    )

    destination().onkeyup = handleChange _
    distance().onkeyup = handleChange _
    distance().onchange = handleChange _

    searchButton().style.display = "none"

    // From http://getbootstrap.com/javascript/#modals-events
    jQuery("#mapModal").on("shown.bs.modal",
      (_: JQueryEventObject) => {
        Client[HotelsService].search(destination().value, distance().value.toDouble).call().foreach { hotels =>
          document.getElementById("mapModalLabel").innerHTML = "Map View"
          renderMap(document.getElementById("map"), hotels)
        }
      }
    )

  }

  def handleChange(e: Event) = {
    reload(destination().value, distance().value.toDouble)
  }

  def reload(destination: String, distance: Double) = {
    for {
      hotels <- Client[HotelsService].search(destination, distance).call()
      table = HotelListingTable(hotels).render
    } hotelsTables().outerHTML = table
  }

  def renderMap(target: Element, hotels: Seq[Hotel]) = {

    val opts = google.maps.MapOptions(
      center = new google.maps.LatLng(50, 0),
      zoom = 11
    )

    val gmap = new google.maps.Map(target, opts)

    val point =
      for {
        hotel <- hotels
        Coordinates(lat, long) = hotel.coordinates
        latLng = new google.maps.LatLng(lat, long)
      } yield {

        val marker = new google.maps.Marker(google.maps.MarkerOptions(
          position = latLng,
          map = gmap,
          title = hotel.name
        ))

        val infoWindow = new google.maps.InfoWindow(
          InfoWindowOptions( content =
            div(
              h2(hotel.name),
              raw(hotel.descriptionHtml)
            ).render
          )
        )

        marker -> infoWindow
      }

    val markerBounds = new google.maps.LatLngBounds()
    var activeInfoWindow = new google.maps.InfoWindow

    for {
      (marker, infoWindow) <- point
    } yield {
      marker.addListener("click", (_:js.Any) => {
        activeInfoWindow.close()
        activeInfoWindow = infoWindow
        infoWindow.open(gmap, marker)
      })
      markerBounds.extend(marker.getPosition())
    }

    gmap.fitBounds(markerBounds)

  }

}


