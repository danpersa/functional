name := "func"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

libraryDependencies += "com.typesafe.akka" % "akka-stream-experimental_2.11" % "1.0-RC3"
libraryDependencies += "com.typesafe.akka" % "akka-http-core-experimental_2.11" % "1.0-RC3"
libraryDependencies += "com.typesafe.akka" % "akka-http-experimental_2.11" % "1.0-RC3"

fork in run := true