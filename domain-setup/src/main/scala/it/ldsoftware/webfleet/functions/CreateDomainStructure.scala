package it.ldsoftware.webfleet.functions

import java.time.ZonedDateTime

import com.google.cloud.firestore.{Firestore, FirestoreOptions}
import it.ldsoftware.webfleet.firestore.compat.{CreateDomainHandler, ExtendedContext}
import it.ldsoftware.webfleet.firestore.model._

class CreateDomainStructure(firestore: Firestore) extends CreateDomainHandler {

  def this() = {
    this(CreateDomainStructure.firestore)
  }

  override def handleDomainCreated(domain: Domain, context: ExtendedContext): Unit = {
    val document = firestore.collection(Collections.Domains).document(domain.id)

    document
      .collection(Collections.LogEvents)
      .document()
      .set(LogEntry(domain.author, Actions.DomainCreated, context.createTime))
      .get()

    document
      .collection(Collections.Contents)
      .document("index")
      .set(
        MinimalContent(
          s"${domain.title} - Home Page",
          domain.author,
          ZonedDateTime.now(),
          published = true
        )
      )
      .get()
  }

}

object CreateDomainStructure {
  val firestore: Firestore = FirestoreOptions.getDefaultInstance.getService
}
