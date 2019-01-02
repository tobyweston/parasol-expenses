name := "parasol-expenses"

version := "1.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "org.seleniumhq.selenium" % "selenium-java" % "3.141.59",
  "io.argonaut" %% "argonaut" % "6.2.2",
  "org.specs2" %% "specs2-core" % "3.9.5" % "test"
)
