package x

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

object SimpleSqlApp {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val sqlc = new SQLContext(sc)
    val logs = sqlc.jsonFile("a.json")
    logs.registerTempTable("logs")
    val rdd = sqlc.sql("select `0`,count(*) from logs group by `0`")
    rdd.foreach(println)
  }
}