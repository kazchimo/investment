name := """investment"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(guice, ws)
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.3" % Test

libraryDependencies ++= Seq(
  "dev.zio"        %% "zio"         % "1.0.0-RC17",
  "joda-time"      % "joda-time"    % "2.10.5",
  "net.codingwell" %% "scala-guice" % "4.2.6"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
