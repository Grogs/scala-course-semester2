package views

import model.Hotel

import scalatags.Text.all._

object HotelListingTable {

    def link(hotel: Hotel) = {
        import hotel._, coordinates._
        s"http://maps.google.com/?q=$name near $lat,$long"
    }

    def render(hotels: Seq[Hotel]) = {
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
                        td(a(href := link(hotel), "Map")),
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
