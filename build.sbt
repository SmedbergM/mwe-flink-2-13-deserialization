ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.18"

lazy val root = (project in file("."))
  .settings(
    name := "mwe-flink-2-13-deserialization"
  )

lazy val flinkDependencies = {
  val groupId = "org.apache.flink"
  val version = "1.15.4"
  Seq(
    groupId % "flink-streaming-java" % version % Provided,
    groupId % "flink-clients" % version % Provided,
  )
}

lazy val circeDependencies = {
  val groupId ="io.circe"
  val version = "0.14.5"
  Seq(
    groupId %% "circe-parser" % version,
    groupId %% "circe-generic" % version,
  )
}

lazy val loggingDependencies = {
  Seq(
    "ch.qos.logback" % "logback-classic" % "1.4.11",
    "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5"
  )
}

libraryDependencies ++= flinkDependencies
libraryDependencies ++= circeDependencies
libraryDependencies ++= loggingDependencies
