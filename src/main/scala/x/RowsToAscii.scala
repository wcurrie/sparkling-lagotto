package x

import java.io.File

import io.github.binaryfoo.lagotto.output.AsciiTable
import org.apache.spark.sql.SchemaRDD
import sbt.IO

object RowsToAscii {

  def apply(rows: Array[org.apache.spark.sql.Row]): String = {
    val tuples = rows.map{ r => Seq(str(r(0)), str(r(1))) }
    AsciiTable.from(Seq(), tuples).toString()
  }

  def str(a: Any) = if (a == null) "" else a.toString
}

object SchemaToAscii {
  def apply(rdd: SchemaRDD): String = {
    val fieldNames = rdd.schema.fieldNames
    val rows = rdd.collect().map(r => fieldNames.zipWithIndex.map{ case (_, i) => RowsToAscii.str(r(i))})

    val table = new AsciiTable(AsciiTable.maximumWidths(rows), rows.length + 1)
    table.addHeader(fieldNames)
    rows.foreach(table.addRow)
    table.toString()
  }

  def write(rdd: SchemaRDD, file: String) = {
    IO.write(new File(file), apply(rdd))
  }
}
