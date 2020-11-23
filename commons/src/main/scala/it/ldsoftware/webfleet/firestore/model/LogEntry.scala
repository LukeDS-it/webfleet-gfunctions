package it.ldsoftware.webfleet.firestore.model

import java.time.ZonedDateTime
import java.util.Date

case class LogEntry(user: String, action: String, date: ZonedDateTime) {
  def getUser: String = user
  def getAction: String = action
  def getDate: Date = Date.from(date.toInstant)
}
