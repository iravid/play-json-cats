lazy val root = (project in file("."))
  .settings(
    organization := "com.iravid",
    name := "play-json-cats",
    version := "0.1-SNAPSHOT",
    scalaVersion := "2.11.8",
    scalacOptions ++= Seq(
      "-deprecation",
      "-encoding", "UTF-8",
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-unchecked",
      "-Xfatal-warnings",
      "-Xlint",
      "-Yno-adapted-args",
      "-Ywarn-dead-code",
      "-Ywarn-numeric-widen",
      "-Ywarn-value-discard",
      "-Xfuture"
    ),
    libraryDependencies ++= Seq(
      "org.typelevel"     %% "cats"      % "0.9.0",
      "com.typesafe.play" %% "play-json" % "2.5.10",
      "org.typelevel"     %% "cats-laws" % "0.9.0" % "test",
      "org.scalatest"     %% "scalatest" % "3.0.1" % "test"
    )
  )
