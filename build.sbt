// Application Name
name := """RECH-System"""

// Application Version
version := "1.0"

// Ebean
// Version : 11.15.x
// Plugin Version : 4.1.3

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.12.6"

crossScalaVersions := Seq("2.11.12", "2.12.4")

libraryDependencies += guice

// Test Database
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.12"
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2"

// Testing libraries for dealing with CompletionStage...
libraryDependencies += "org.assertj" % "assertj-core" % "3.6.2" % Test
libraryDependencies += "org.awaitility" % "awaitility" % "2.0.0" % Test

libraryDependencies += "tyrex" % "tyrex" % "1.0.1"

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
