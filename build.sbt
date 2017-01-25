lazy val root = (project in file("."))
  .settings(
    organization := "com.iravid",
    name := "play-json-cats",
    version := "0.1-SNAPSHOT",
    scalaVersion := "2.11.8",
    libraryDependencies ++= Seq(
      "org.typelevel"     %% "cats"      % "0.9.0",
      "com.typesafe.play" %% "play-json" % "2.5.10",
      "org.typelevel"     %% "cats-laws" % "0.9.0" % "test",
      "org.scalatest"     %% "scalatest" % "3.0.1" % "test"
    )
  )

