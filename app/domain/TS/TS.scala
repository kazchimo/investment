package domain.TS

import org.joda.time.DateTime
import play.api.libs.json.{Json, OWrites}

case class TS(value: Long) { require(value >= 0) }
object TS {
  def fromString(value: String): TS = TS(DateTime.parse(value).getMillis)
  implicit def writes: OWrites[TS] = Json.writes[TS]
}
