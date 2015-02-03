package x

import io.github.binaryfoo.lagotto.ISO8601TimeFormat
import io.github.binaryfoo.lagotto.JposTimestamp.parse

object Time {

  def diff(left: String, right: String): Long = parse(left).getMillis - parse(right).getMillis

  def diffIso(left: String, right: String): Long = ISO8601TimeFormat.parseDateTime(left).getMillis - ISO8601TimeFormat.parseDateTime(right).getMillis
}
