package it.ldsoftware.webfleet.firestore.model

import java.time.ZonedDateTime
import java.util.Date

case class MinimalContent(
    title: String,
    author: String,
    createdAt: ZonedDateTime,
    published: Boolean
) {
  def getTitle: String = title
  def getAuthor: String = author
  def getCreatedAt: Date = Date.from(createdAt.toInstant)
  def getPublished: Boolean = published
}
