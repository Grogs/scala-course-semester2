package services.hotels

import org.jsoup.Jsoup
import org.scalatest.{FunSuite, Matchers}
import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._

import scala.collection.JavaConverters._

class Lesson7 extends FunSuite with WsScalaTestClient with OneAppPerTest with Matchers {

    /*

      See http://getbootstrap.com/javascript/#modals

    */


    test("display results in a table"){

        val body = route(app, FakeRequest(GET, "/hotels/search?destination=london&distance=1.2")).map(contentAsString).get

        val button = Jsoup.parse(body).select("button").first()

        button.classNames.asScala should contain ("btn")
        button.id shouldBe "show-map"
    }

    test("bootstrap modal for the map"){

        val body = route(app, FakeRequest(GET, "/hotels/search?destination=london&distance=1.2")).map(contentAsString).get

        val maybeModal = Jsoup.parse(body).select("div.modal").asScala.headOption

        maybeModal shouldBe 'defined

        val modal = maybeModal.get

        modal.id shouldBe "mapModal"
    }

    test("button should be configurd to open the modal"){

        val body = route(app, FakeRequest(GET, "/hotels/search?destination=london&distance=1.2")).map(contentAsString).get

        val modal = Jsoup.parse(body).select("div.modal").first

        modal.attr("data-toggle") shouldBe "modal"
        modal.attr("data-target") shouldBe "#mapModal"
    }

    test("modal should include a container for the google map"){

        val body = route(app, FakeRequest(GET, "/hotels/search?destination=london&distance=1.2")).map(contentAsString).get

        val modal = Jsoup.parse(body).select("div.modal").first

        modal.select(".map")
    }

}
