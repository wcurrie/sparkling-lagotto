package x

import ghetto.{FilePosition, JposEntryHolder, JposLogInputFormat}
import org.apache.spark.{SparkConf, SparkContext}

object SimpleInputFormatApp {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val rdd = sc.hadoopFile[FilePosition, JposEntryHolder]("jpos.log", classOf[JposLogInputFormat], classOf[FilePosition], classOf[JposEntryHolder])
    rdd.map { case (p, h) => h.entry }
      .groupBy(_.mti)
      .sortBy(_._2.size)
      .foreach { case (mti, entries) => println(mti + " " + entries.size)}
  }
}
