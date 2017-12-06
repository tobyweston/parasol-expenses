name := "parasol-expenses"

version := "1.0"

scalaVersion := "2.12.3"

libraryDependencies ++= Seq(
  "org.seleniumhq.selenium" % "selenium-java" % "3.6.0",
  "io.argonaut" %% "argonaut" % "6.2",
  "org.specs2" %% "specs2-core" % "3.9.5" % "test"
)
