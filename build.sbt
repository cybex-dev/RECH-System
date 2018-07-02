// Application Name
name := """RECH-System"""

// Application Version
version := "1.0"

// Ebean
// Version : 11.15.x
// Plugin Version : 4.1.3

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean, ScalaJSPlugin, ScalaJSWeb, JavaAppPackaging, JavaServerAppPackaging, LauncherJarPlugin)

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies += playCore
libraryDependencies += akkaHttpServer
libraryDependencies += guice

libraryDependencies += jdbc
libraryDependencies += evolutions
libraryDependencies += ehcache
libraryDependencies += ws

// Play Mailer (using dependancy injection via Guice)
libraryDependencies += "com.typesafe.play" %% "play-mailer" % "6.0.1"
libraryDependencies += "com.typesafe.play" %% "play-mailer-guice" % "6.0.1"

// Test Database
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.12"
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2"

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test

libraryDependencies += "tyrex" % "tyrex" % "1.0.1"

// Using webjars for Bootstrap 4.1.1
libraryDependencies += "org.webjars" % "bootstrap" % "4.1.1"
libraryDependencies += "org.webjars" %% "webjars-play" % "2.6.3"
libraryDependencies += "org.webjars" % "requirejs" % "2.3.5"
libraryDependencies += "org.webjars" % "popper.js" % "1.14.1"

// See: https://adrianhurt.github.io/play-bootstrap#Installation
// Resolver is needed only for SNAPSHOT versions
resolvers += "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies += "com.adrianhurt" %% "play-bootstrap" % "1.4-P26-B4-SNAPSHOT"

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
