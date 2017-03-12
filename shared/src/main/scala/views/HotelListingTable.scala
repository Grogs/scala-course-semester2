package views

import model.Hotel

import scalatags.Text.all._

object HotelListingTable {

  def gmapsLink(hotel: Hotel) = {
    import hotel._, coordinates._
    s"http://maps.google.com/?q=$name near $lat,$long"
  }

  /*
      Original Twirl template:

        <table class="table">
          <tr>
              <th>Name</th>
              <th>Location</th>
              <th>Images</th>
          </tr>
          @for(hotel <- searchResults) {
          <tr>
              <td>@hotel.name</td>
              <td><a href="@gmapsLink(hotel)">Map</a></td>
              <td>
              @for(image <- hotel.images) {
                  <img height="200px" src="@image" />
              }
              </td>
          </tr>
          }
      </table>
   */

  def apply(hotels: Seq[Hotel]) = {
    table(id := "hotels", `class` := "table",
      thead(
        tr(
          th("Name"),
          th("Location"),
          th("Images")
        )
      ),
      tbody(
        for (hotel <- hotels) yield {
          tr(
            td(hotel.name),
            td(a(href := gmapsLink(hotel), "Map")),
            td(
              for (i <- hotel.images) yield
                img(height := 200.px, src := i)
            )
          )
        }
      )
    )

  }


}
