import sbt._
import Keys._

val scioVersion = "0.7.4"
val beamVersion = "2.11.0"
val scalaMacrosVersion = "2.1.1"

lazy val commonSettings = Defaults.coreDefaultSettings ++ Seq(
  organization := "org.bom4v.ti",
  organizationName := "Business Object Models for Verticals (BOM4V)",
  organizationHomepage := Some(url("http://github.com/bom4v")),
  homepage := Some(url("https://github.com/bom4v/induction-beam-scio")),
  startYear := Some(2019),
  licenses += "Apache-2.0" -> url("https://www.apache.org/licenses/LICENSE-2.0"),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/bom4v/induction-beam-scio"),
      "https://github.com/bom4v/induction-beam-scio.git"
    )
  ),
  developers := List(
    Developer(
      id    = "denis.arnaud",
      name  = "Denis Arnaud",
      email = "denis.arnaud_ossrh@m4x.org",
      url   = url("https://github.com/denisarnaud")
    )
  ),
  // Semantic versioning http://semver.org/
  version := "0.0.1",
  scalaVersion := "2.12.8",
  scalacOptions ++= Seq("-target:jvm-1.8",
                        "-deprecation",
                        "-feature",
                        "-unchecked"),
  javacOptions ++= Seq("-source", "1.8", "-target", "1.8")
)

lazy val paradiseDependency =
  "org.scalamacros" % "paradise" % scalaMacrosVersion cross CrossVersion.full
lazy val macroSettings = Seq(
  libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  addCompilerPlugin(paradiseDependency)
)

lazy val root: Project = project
  .in(file("."))
  .settings(commonSettings)
  .settings(macroSettings)
  .settings(
    name := "beam-induction",
    description := "Induction for Scala-based Apache Beam (SCIO) data pipelines",
    libraryDependencies ++= Seq(
      "com.spotify" %% "scio-core" % scioVersion,
      "com.spotify" %% "scio-test" % scioVersion % Test,
      "org.apache.beam" % "beam-runners-direct-java" % beamVersion,
      // optional dataflow runner
      // "org.apache.beam" % "beam-runners-google-cloud-dataflow-java" % beamVersion,
      "org.slf4j" % "slf4j-simple" % "1.7.25"
    ),
    pomIncludeRepository := { _ => false },
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    publishMavenStyle := true,
    publishConfiguration := publishConfiguration.value.withOverwrite(true),
    publishLocalConfiguration := publishLocalConfiguration.value.withOverwrite(true),
    cleanKeepFiles += target.value / "test-reports"
  )
  .enablePlugins(PackPlugin)

lazy val repl: Project = project
  .in(file(".repl"))
  .settings(commonSettings)
  .settings(macroSettings)
  .settings(
    name := "repl",
    description := "Scio REPL for word-count",
    libraryDependencies ++= Seq(
      "com.spotify" %% "scio-repl" % scioVersion
    ),
    Compile / mainClass := Some("com.spotify.scio.repl.ScioShell"),
    publish / skip := true
  )
  .dependsOn(root)

