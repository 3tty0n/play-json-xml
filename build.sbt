lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.github.3tty0n",
      scalaVersion := "2.12.1",
      crossScalaVersions := Seq("2.12.1", "2.11.8"),
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "json-xml-converter",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.1",
      "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
    ) ++ (
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, scalaMajor)) if scalaMajor >= 12 =>
          "com.typesafe.play" %% "play-json" % "2.6.0-M6" :: Nil
        case _ =>
          "com.typesafe.play" %% "play-json" % "2.5.13" :: Nil
      }
    )
  )
