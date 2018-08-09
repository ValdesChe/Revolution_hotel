import sbt.Keys._

name := """Golden Hotel """

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.5"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  "org.mindrot" % "jbcrypt" % "0.3m",
  "com.typesafe.play" %% "play-mailer" % "3.0.1",
  "joda-time" % "joda-time" % "2.9.9",
  "mysql" % "mysql-connector-java" % "5.1.36",
  evolutions,
  filters
)


resolvers ++= Seq(
    "Apache" at "http://repo1.maven.org/maven2/",
    "jBCrypt Repository" at "http://repo1.maven.org/maven2/org/",
    "Sonatype OSS Snasphots" at "http://oss.sonatype.org/content/repositories/snapshots"
)
routesGenerator := InjectedRoutesGenerator

lazy val root = (project in file(".")).enablePlugins(play.PlayJava, PlayEbean)



fork in run := false