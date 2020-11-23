val gcloudVersion = "1.0.1"
val gsonVersion = "2.8.6"
val firestoreVersion = "2.1.0"
val scalaLoggingVersion = "3.9.2"
val logbackVersion = "1.2.3"
val logstashLogbackEncoderVersion = "5.2"
val scalatestVersion = "3.1.1"
val scalatestMockitoVersion = "1.0.0-M2"

val deps = Seq(
  "com.google.cloud.functions" % "functions-framework-api" % gcloudVersion,
  "com.google.code.gson" % "gson" % gsonVersion,
  "ch.qos.logback" % "logback-classic" % logbackVersion,
  "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
  "net.logstash.logback" % "logstash-logback-encoder" % logstashLogbackEncoderVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion % Test,
  "org.scalatestplus" %% "scalatestplus-mockito" % scalatestMockitoVersion % Test
)

lazy val commons = project
  .settings(CommonSettings.settings)
  .settings(
    libraryDependencies ++= deps
  )

lazy val `domain-setup` = project
  .dependsOn(commons)
  .enablePlugins(JavaAppPackaging)
  .settings(CommonSettings.settings)
  .settings(AssemblySettings.settings)
  .settings(
    libraryDependencies ++= Seq(
      "com.google.cloud" % "google-cloud-firestore" % firestoreVersion,
    )
  )

