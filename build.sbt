lazy val root = (project in file("."))
  .settings(
    organization := "com.iravid",
    name := "play-json-cats",
    version := "1.0.0",
    scalaVersion := "2.11.12",
    crossScalaVersions := Seq("2.11.12", "2.12.4"),
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
      "org.typelevel"     %% "cats-core" % "1.0.1",
      "com.typesafe.play" %% "play-json" % "2.6.8",
      "org.typelevel"     %% "cats-laws" % "1.0.1" % "test",
      "org.scalatest"     %% "scalatest" % "3.0.4" % "test"
    ),
    licenses += ("Apache-2.0", url("https://opensource.org/licenses/Apache-2.0")),
    homepage := Some(url("https://github.com/iravid/play-json-cats")),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/iravid/play-json-cats"),
        "scm:git@github.com:iravid/play-json-cats.git"
      )
    ),
    developers := List(
      Developer(
        id = "iravid",
        name = "Itamar Ravid",
        email = "iravid@iravid.com",
        url = url("https://github.com/iravid")
      )
    ),
    publishMavenStyle := true,
    publishTo := sonatypePublishTo.value
  )
