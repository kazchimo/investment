package adapter.controllers.coinCheck

import play.api.libs.json.{Json, Reads}

case class MarketBuyRequest(amount: Double)
object MarketBuyRequest {
  implicit def marketBuyRequestReads: Reads[MarketBuyRequest] = Json.reads[MarketBuyRequest]
}
