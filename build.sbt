lazy val Scala212 = "2.12.1"
lazy val Scala211 = "2.11.8"
lazy val Scala210 = "2.10.6"

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      name                := "play-json-xml",
      organization        := "com.github.3tty0n",
      scalaVersion        := Scala212,
      crossScalaVersions  := Scala212 :: Scala211 :: Scala210 :: Nil,
      version             := "0.1.0-SNAPSHOT"
    )),
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.1",
      "org.scala-lang.modules" %% "scala-xml" % "1.0.6"
    ) ++ (
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, scalaMajor)) if scalaMajor >= 12 =>
          "com.typesafe.play" %% "play-json" % "2.6.0-M6" :: Nil
        case Some((2, scalaMajor)) if scalaMajor == 11 =>
          "com.typesafe.play" %% "play-json" % "2.5.13" :: Nil
        case _ =>
          "com.typesafe.play" %% "play-json" % "2.4.11" :: Nil
      }
    )
  )
