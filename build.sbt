lazy val root = (project in file("."))
  .settings(
    organization := "com.iravid",
    name := "play-json-cats",
    version := "0.2",
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
    bintrayReleaseOnPublish in ThisBuild := false,
    bintrayPackageLabels := Seq("play-json", "cats", "json"),
    scaladexKeywords in Scaladex := Seq("play-json", "cats", "json"),
    credentials in Scaladex += Credentials(Path.userHome / ".ivy2" / ".scaladex.credentials")
  )
