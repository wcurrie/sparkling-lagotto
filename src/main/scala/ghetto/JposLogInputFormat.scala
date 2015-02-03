package ghetto

import io.github.binaryfoo.lagotto.JposEntry
import io.github.binaryfoo.lagotto.reader.EntryIterator
import org.apache.hadoop.fs.{FSDataInputStream, Path}
import org.apache.hadoop.mapred._

import scala.io.Source

class JposLogInputFormat extends InputFormat[FilePosition, JposEntryHolder] {

  override def getSplits(job: JobConf, numSplits: Int): Array[InputSplit] = {
    FileInputFormat.getInputPaths(job).map{p =>
      val fs = p.getFileSystem(job)
      val len = fs.getFileStatus(p).getLen
      new FileSplit(p, 0, len, Array("localhost"))
    }
  }

  override def getRecordReader(split: InputSplit, job: JobConf, reporter: Reporter): RecordReader[FilePosition, JposEntryHolder] = {
    val fileSplit = split.asInstanceOf[FileSplit]
    val path = fileSplit.getPath
    val fs = path.getFileSystem(job)
    val status = fs.getFileStatus(path)
    val stream = fs.open(path)
    new JposRecordReader(stream, status.getLen, path)
  }
}

class JposRecordReader(val stream: FSDataInputStream, val len: Long, val path: Path) extends RecordReader[FilePosition, JposEntryHolder] {

  private val iterator = new EntryIterator[JposEntry](Source.fromInputStream(stream), path.toString)

  override def next(key: FilePosition, value: JposEntryHolder): Boolean = {
    if (!iterator.hasNext) {
      return false
    }
    val entry = iterator.next()
    key.line = entry.source.line
    value.entry = entry
    true
  }

  override def getProgress: Float = stream.getPos.toFloat / len

  override def getPos: Long = stream.getPos

  override def createKey(): FilePosition = new FilePosition(path, 0 )

  override def close(): Unit = stream.close()

  override def createValue(): JposEntryHolder = new JposEntryHolder(null)
}

class JposEntryHolder(var entry: JposEntry)

class FilePosition(var file: Path, var line: Int)

