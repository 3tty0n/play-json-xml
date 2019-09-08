lazy val Scala213 = "2.13.0"
lazy val Scala212 = "2.12.4"

lazy val root = (project in file(".")).
  settings(
    name                := "play-json-xml",
    organization        := "org.micchon",
    scalaVersion        := Scala212,
    crossScalaVersions  := Scala213 :: Scala212 :: Nil,
    version             := "0.4.3-SNAPSHOT",
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "3.0.8" % Test,
      "org.scala-lang.modules" %% "scala-xml" % "1.2.0",
      "com.typesafe.play" %% "play-json" % "2.7.4"
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
