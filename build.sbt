lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.13.3"
    )),
    name := "scalatest-example"
    
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" //% Test

libraryDependencies += "org.log4s" %% "log4s" % "1.8.2"
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.3" % Runtime


