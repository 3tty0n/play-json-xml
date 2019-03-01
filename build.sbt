lazy val Scala212 = "2.12.4"
lazy val Scala211 = "2.11.11"

lazy val root = (project in file(".")).
  settings(
    name                := "play-json-xml",
    organization        := "org.micchon",
    scalaVersion        := Scala212,
    crossScalaVersions  := Scala212 :: Scala211 :: Nil,
    version             := "0.3.2",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.3" % Test
    ) ++ (
      CrossVersion.partialVersion(scalaVersion.value) match {
        case Some((2, scalaMajor)) if scalaMajor >= 12 =>
          Seq("org.scala-lang.modules" %% "scala-xml" % "1.0.6",
              "com.typesafe.play" %% "play-json" % "2.7.1")
        case Some((2, scalaMajor)) if scalaMajor >= 11 =>
          Seq("org.scala-lang.modules" %% "scala-xml" % "1.0.6",
              "com.typesafe.play" %% "play-json" % "2.5.13")
        case _ =>
          Seq("com.typesafe.play" %% "play-json" % "2.4.11")
      }
    )
  ).
  settings(
    publishSettings: _*
  )

lazy val publishSettings = Seq(
    publishMavenStyle := true,
    publishTo := {
      val nexus = "https://oss.sonatype.org/"
      if (isSnapshot.value)
        Some("snapshots" at nexus + "content/repositories/snapshots")
      else
        Some("releases" at nexus + "service/local/staging/deploy/maven2")
    },
    publishArtifact in Test := false,
    pomIncludeRepository := { _ => false },
    licenses := Seq("Apache 2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    pomExtra :=
      <url>https://github.com/3tty0n/play-json-xml</url>
      <developers>
        <developer>
          <id>3tty0n</id>
          <name>Yusuke Izawa</name>
          <url>http://micchon.org</url>
        </developer>
      </developers>
      <scm>
        <url>git@github.com:3tty0n/play-json-xml.git</url>
        <connection>scm:git:git@github.com:3tty0n/play-json-xml.git</connection>
      </scm>
  )
