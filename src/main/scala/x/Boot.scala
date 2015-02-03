package x

import io.github.binaryfoo.lagotto.Iso8583
import org.apache.spark.sql.{SQLContext, SchemaRDD}
import org.apache.spark.{SparkConf, SparkContext}

object Boot {

  val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
  val sc = new SparkContext(conf)
  val sqlc = new SQLContext(sc)

  sqlc.registerFunction("isResponseMti", Iso8583.isResponseMTI _)
  sqlc.registerFunction("invertMti", Iso8583.invertMTI _)
  sqlc.registerFunction("timeDiff", Time.diffIso _)

  def loadLogs(file: String): SchemaRDD = {
    val logs = sqlc.jsonFile(file)
    logs.registerTempTable("logs")
    logs
  }

}
