package it.ldsoftware.webfleet.firestore.compat

import java.time.ZonedDateTime

import com.google.cloud.functions.Context

case class ExtendedContext(context: Context, createTime: ZonedDateTime, updateTime: ZonedDateTime)

object ExtendedContext {
  def apply(context: Context, createTime: ZonedDateTime, updateTime: ZonedDateTime): ExtendedContext = new ExtendedContext(context, createTime, updateTime)
}
