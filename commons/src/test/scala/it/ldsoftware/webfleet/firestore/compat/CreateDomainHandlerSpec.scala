package it.ldsoftware.webfleet.firestore.compat

import java.time.{LocalDate, LocalTime, ZoneId, ZonedDateTime}

import it.ldsoftware.webfleet.firestore.model.Domain
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import org.scalatestplus.mockito.MockitoSugar

import scala.io.Source

class CreateDomainHandlerSpec extends AnyWordSpec with Matchers with MockitoSugar {

  private val json = Source.fromResource("example-create-domain.json").getLines().mkString

  private val subject = new CreateDomainHandler {
    override def handleDomainCreated(domain: Domain, context: ExtendedContext): Unit = {}
  }

  private val testedValue = subject.extractPayload(json).getAsJsonObject("value")

  "getDomain" should {
    "correctly parse domain information from a firestore event" in {
      subject.getDomain(testedValue) shouldBe Domain(
        "domain-unique-id",
        "Website Title",
        "author@email.com",
        "icon-string-value"
      )
    }
  }

  "getCreateTime" should {
    "correctly return a java date time with zone" in {
      subject.getCreateTime(testedValue) shouldBe ZonedDateTime.of(
        LocalDate.of(2020, 11, 23),
        LocalTime.of(18, 6, 11, 784255000),
        ZoneId.of("Z")
      )
    }
  }

  "getUpdateTime" should {
    "correctly return a java date time with zone" in {
      subject.getUpdateTime(testedValue) shouldBe ZonedDateTime.of(
        LocalDate.of(2020, 11, 23),
        LocalTime.of(18, 6, 11, 784255000),
        ZoneId.of("Z")
      )
    }
  }

}
