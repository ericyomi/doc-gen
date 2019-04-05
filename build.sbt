name := "doc-gen"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.15"
libraryDependencies += "org.flywaydb" % "flyway-core" % "5.2.4"
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.3.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.0"
)