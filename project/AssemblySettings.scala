import sbt._
import sbt.Keys._
import sbtassembly.AssemblyPlugin.autoImport._

object AssemblySettings {

  lazy val settings: Seq[Def.Setting[_]] = Seq(
    assemblyJarName in assembly := s"deploy/${name.value}-function.jar",
    assemblyMergeStrategy in assembly := {
      case "module-info.class" => MergeStrategy.discard
      case x =>
        val oldStrategy = (assemblyMergeStrategy in assembly).value
        oldStrategy(x)
    }
  )

}
