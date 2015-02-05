import ghetto.{FilePosition, JposEntryHolder, JposLogInputFormat}
import org.apache.spark.{SparkConf, SparkContext}
import org.scalatest.{Matchers, FlatSpec}

class JposLogInputFormatTest extends FlatSpec with Matchers {

  it should "parse a few entries" in {
    val conf = new SparkConf().setAppName("Simple Application").setMaster("local")
    val sc = new SparkContext(conf)
    val rdd = sc.hadoopFile[FilePosition, JposEntryHolder]("src/test/resources/a-bunch.xml", classOf[JposLogInputFormat], classOf[FilePosition], classOf[JposEntryHolder])
    val entries = rdd.map { case (p, h) => h.entry }.map(_.toCsv("time", "mti")).toLocalIterator.toSeq
    entries shouldBe Seq("00:00:03.292,0200", "00:00:04.292,0200", "00:00:04.892,0210", "00:00:04.992,0210")
  }

}
