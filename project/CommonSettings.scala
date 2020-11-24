import sbt._
import sbt.Keys._

object CommonSettings {
  val projectScalaVersion: String = "2.13.3"

  private val scalaCompilerOptions = Seq(
    "-encoding",  // Specify character encoding used by source files
    "UTF-8",
    "-deprecation", // Emit warning and location for usages of deprecated APIs.
    "-unchecked", // Enable detailed unchecked (erasure) warnings
    "-feature", // more verbose warning on language features that have not been enabled
    "-Xlint", // Enable recommended additional warnings.
    "-Ywarn-dead-code", // compiler generates warnings for some unreachable pieces of code
    "-Ywarn-numeric-widen" // warning on numeric implicit conversions to a wider type
  )

  private val javaCompilerOptions = Seq("-source", "1.8", "-target", "1.8", "-Xlint:unchecked", "-Xlint:deprecation", "-Xlint:-options")

  /**
   * Common project settings
   */
  lazy val settings: Seq[Def.Setting[_]] =
    Seq(
      scalacOptions in Compile ++= scalaCompilerOptions,
      (scalacOptions in (Compile, doc)) ++= (name in (Compile, doc))
        .zip(version in (Compile, doc))
        .map { case (n, v) => DefaultOptions.scaladoc(n, v) }
        .value,
      javacOptions in (Compile, compile) ++= javaCompilerOptions,
      javacOptions in doc := Seq(),
      javaOptions += "-Xmx1G",
      outputStrategy := Some(StdoutOutput),
      exportJars := true,
      fork in run := true,
      fork in Test := true,
      sbtPlugin := false,
      scalaVersion := projectScalaVersion
    )
}
