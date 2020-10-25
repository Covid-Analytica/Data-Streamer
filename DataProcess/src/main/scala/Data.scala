import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

import com.mongodb.spark.config._
import com.mongodb.spark.{MongoSpark, toSparkContextFunctions}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object Data {



  def getCovid(date: Int, daysPeriod: Int): RDD[(AnyRef, Integer)] = {
    SparkSession.clearActiveSession()
    val spark = SparkSession.builder()
      .master("local")
      .appName("Covid")
      .config("spark.mongodb.input.uri", "mongodb://127.0.0.1/DataIntensive_DB.covid")
      .getOrCreate()

    val readConfig = ReadConfig(Map("collection" -> "covid"), Some(ReadConfig(spark.sparkContext)))
    val rdd = MongoSpark.load(spark.sparkContext, readConfig)

    val sc = spark.sparkContext

    //val rdd = MongoSpark.load(sc)

    val dateTo = addDays(date, daysPeriod)


    val filteredRdd = rdd.filter(doc => doc.getInteger("date") > date && doc.getInteger("date") < dateTo)

    val map = filteredRdd.map(doc => (doc.get("state"), doc.getInteger("newCases")))

    map.reduceByKey((x, y) => x + y)
  }


  def getEvents(date: Integer): RDD[(AnyRef, Integer)]  = {
    val spark = SparkSession.builder()
      .master("local")
      .appName("Events")
      .config("spark.mongodb.input.uri", "mongodb://127.0.0.1/DataIntensive_DB.events")
      .getOrCreate()

    val readConfig = ReadConfig(Map("collection" -> "events"), Some(ReadConfig(spark.sparkContext)))
    val rdd = MongoSpark.load(spark.sparkContext, readConfig)

    val sc = spark.sparkContext

    //val rdd = MongoSpark.load(sc)

    val filteredRdd = rdd.filter(doc => doc.getInteger("st_time").equals(date))

    val map = filteredRdd.map(doc => (doc.get("state"), doc.getInteger("yes_rsvp_count")))
    //val map = filteredRdd.map(doc => (doc.get("state"), (1, doc.getInteger("yes_rsvp_count"))))

    //map.reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))
    map.reduceByKey((x, y) => x + y)
  }


  def addDays(date: Int, days: Int): Int = {
    val format = new SimpleDateFormat("yyyyMMdd")
    val currentDate = format.parse(date.toString)
    val c = Calendar.getInstance()
    c.setTime(currentDate)
    c.add(Calendar.DATE, days)
    val newDate = c.getTime
    Integer.parseInt(format.format(newDate))
  }
}