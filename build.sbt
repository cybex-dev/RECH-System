// Application Name
name := """RECH-System"""

// Application Version
version := "1.0"

// Ebean
// Version : 11.15.x
// Plugin Version : 4.1.3

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayScala, PlayEbean, ScalaJSPlugin, JavaAppPackaging, JavaServerAppPackaging, LauncherJarPlugin)

scalaVersion := "2.12.6"

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

// Database
libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.12"

libraryDependencies += "tyrex" % "tyrex" % "1.0.1"

// Using webjars for Bootstrap 4.1.1
libraryDependencies += "org.webjars" % "bootstrap" % "4.1.1"
libraryDependencies += "org.webjars" %% "webjars-play" % "2.6.3"
libraryDependencies += "org.webjars" % "requirejs" % "2.3.5"
libraryDependencies += "org.webjars" % "popper.js" % "1.14.1"

// Hashing for user passwords
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.3m"

// Make verbose tests
testOptions in Test := Seq(Tests.Argument(TestFrameworks.JUnit, "-a", "-v"))
