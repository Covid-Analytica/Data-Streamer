import com.mongodb.spark.{MongoSpark, toSparkContextFunctions}
import com.mongodb.spark.config._
import org.apache.spark.sql.{DataFrame, Dataset, Row, SparkSession}
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions
import org.bson.Document


object Process  extends App {
  SparkSession.clearActiveSession()
  val spark = SparkSession.builder()
    .master("local")
    .appName("covid")
    .config("spark.mongodb.input.uri", "mongodb://127.0.0.1/DataIntensive_DB.covid")
    .config("spark.mongodb.output.uri", "mongodb://127.0.0.1/DataIntensive_DB.covid_report")
    .getOrCreate()

  val readConfig = ReadConfig(Map("collection" -> "covid"), Some(ReadConfig(spark.sparkContext)))
  val rdd = MongoSpark.load(spark.sparkContext, readConfig)
  val writeConfig = WriteConfig(Map("collection" -> "covid_report", "writeConcern.w" -> "majority"), Some(WriteConfig(spark.sparkContext)))
  val dates = rdd.map(doc => (doc.getInteger("date")))
  val distinctDate = dates.distinct().collect().toList

  distinctDate.foreach(x => {
    val covid = Data.getCovid(x, 14)
    val events = Data.getEvents(x)
    val joined = covid.leftOuterJoin(events).cache()
    val documents = joined.map(result => Document.parse(s"{date: $x, state : ${'"'}${result._1}${'"'}, cases: ${result._2._1}, rsvp: ${'"'}${result._2._2}${'"'}}"))
    MongoSpark.save(documents, writeConfig)
  })
}


