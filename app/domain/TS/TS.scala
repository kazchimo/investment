package domain.TS

import org.joda.time.DateTime

case class TS(value: Long) { require(value >= 0) }
object TS {
  def fromString(value: String): TS = TS(DateTime.parse(value).getMillis)
}
