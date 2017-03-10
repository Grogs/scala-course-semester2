package services.hotels

import org.scalatestplus.play._

class HotelsControllerIntegrationSpec extends PlaySpec with OneServerPerSuite with OneBrowserPerSuite with HtmlUnitFactory {

  "The search page" should {

    "work from within a browser" in {

      go to (s"http://localhost:$port/hotels/search?destination=london&distance=10")

      pageTitle must be ("Hotels within 10 kilometers of london")

      pageSource must include ("DoubleTree by Hilton London Victoria")
    }
  }
}
