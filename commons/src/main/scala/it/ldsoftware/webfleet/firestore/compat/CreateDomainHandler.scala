package it.ldsoftware.webfleet.firestore.compat

import java.time.ZonedDateTime

import com.google.cloud.functions.{Context, RawBackgroundFunction}
import com.google.gson.{Gson, JsonObject}
import com.typesafe.scalalogging.LazyLogging
import it.ldsoftware.webfleet.firestore.model.Domain

trait CreateDomainHandler extends RawBackgroundFunction with LazyLogging {

  private val gson = new Gson()

  private val DomainRegex = """.*/domains/(.*)""".r

  def handleDomainCreated(domain: Domain, context: ExtendedContext): Unit

  override def accept(json: String, context: Context): Unit = {
    val payload = extractPayload(json)
    val value = payload.getAsJsonObject("value")

    val domain = getDomain(value)
    val updateTime = getUpdateTime(value)
    val createTime = getCreateTime(value)

    handleDomainCreated(domain, ExtendedContext(context, createTime, updateTime))
  }

  def extractPayload(json: String): JsonObject = gson.fromJson(json, classOf[JsonObject])

  def getCreateTime(value: JsonObject): ZonedDateTime =
    ZonedDateTime.parse(value.get("createTime").getAsString)

  def getUpdateTime(value: JsonObject): ZonedDateTime =
    ZonedDateTime.parse(value.get("updateTime").getAsString)

  def getDomain(value: JsonObject): Domain = {
    val domainFields = value.getAsJsonObject("fields")
    val id = value.get("name").getAsString match {
      case DomainRegex(pathName) => pathName
    }

    Domain(
      id,
      domainFields.getAsJsonObject("title").get("stringValue").getAsString,
      domainFields.getAsJsonObject("author").get("stringValue").getAsString,
      domainFields.getAsJsonObject("icon").get("stringValue").getAsString
    )
  }
}
