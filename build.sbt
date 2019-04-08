name := "crawler"

version := "0.1"

scalaVersion := "2.12.8"

//util dependencies
libraryDependencies ++= Seq(
  "org.jsoup" % "jsoup" % "1.11.3",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2"
)