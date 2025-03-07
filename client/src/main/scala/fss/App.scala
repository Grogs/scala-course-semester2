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

}


