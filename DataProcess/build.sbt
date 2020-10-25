name := "DataProcess"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "2.4.3",
  "org.mongodb.scala" %% "mongo-scala-driver" % "2.9.0",
  "org.mongodb.spark" %% "mongo-spark-connector" % "2.4.2",
)