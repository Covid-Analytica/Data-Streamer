import java.io.Serializable

import com.mongodb.spark.MongoSpark
import com.mongodb.spark.config._
import com.mongodb.spark.sql._
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.rdd.RDD
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions
import org.bson.Document
import org.json4s.DoubleJsonFormats.GenericFormat
import org.json4s.jsonwritable
import org.mongodb
import org.mongodb.scala._

import scala.concurrent.Await
import scala.concurrent.duration.Duration


object Process  extends App {

//  val spark = SparkSession.builder()
//    .master("local")
//    .appName("covid")
//    .config("spark.mongodb.input.uri", "mongodb://127.0.0.1/DataIntensive_DB.covid")
//    .config("spark.mongodb.output.uri", "mongodb://127.0.0.1/DataIntensive_DB.covid_report")
//    .getOrCreate()
//
//  val readConfig = ReadConfig(Map("collection" -> "covid"), Some(ReadConfig(spark.sparkContext)))
//  val rdd = MongoSpark.load(spark.sparkContext, readConfig)
//  val writeConfig = WriteConfig(Map("collection" -> "covid_report", "writeConcern.w" -> "majority"), Some(WriteConfig(spark.sparkContext)))
//  val dates = rdd.map(doc => (doc.getInteger("date")))
//
//  val distinct = dates.distinct()
//
//  println(distinct.count())
//
//
//  distinct.foreach(x => {
//    println(x)
//    val covid = Data.getCovid(x, 14)
//    val events = Data.getEvents(x)
//    val joined = covid.leftOuterJoin(events)
//
//    val documents = joined.map(report => Document.parse(s"report: $report"))
//    MongoSpark.save(documents, writeConfig)
//    println("Finish")
//  })

  val date = 20200719
  val covid = Data.getCovid(date, 14)
  val events = Data.getEvents(date)

  val joined = covid.leftOuterJoin(events)

  joined.foreach(x => println(x))

}


