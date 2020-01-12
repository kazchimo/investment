package infra.exchange

import com.google.inject.Inject
import domain.TS.TS
import domain.exchange.Exchange
import domain.financialProduct.{BitCoin, JPY}
import domain.order.{ContractedMarketBuy, MarketBuy}
import play.api.http.{ContentTypes, HeaderNames}
import play.api.libs.json.{JsValue, Json, Reads, Writes}
import play.api.libs.ws.WSClient
import zio.ZIO

class CoinCheckExchange @Inject()(ws: WSClient) extends Exchange with HeaderNames with ContentTypes {
  def marketBuy(order: MarketBuy) = CoinCheckApi.Orders.marketBuy(order)

  private object CoinCheckApi {
    private val RouteUrl     = "https://coincheck.com"
    private val OrderTypeKey = "order_type"

    object Orders {
      private def request =
        (body: JsValue) =>
          ws.url(RouteUrl + "/api/exchange/orders")
            .withHttpHeaders(ACCEPT -> JSON)
            .post(body)

      private case class Response(
          success: Boolean,
          id: Long,
          rate: String,
          amount: String,
          orderType: String,
          pair: String,
          createdAt: String
      ) {
        def doubleAmount: Double = this.amount.toDouble
        def ts: TS               = TS.fromString(this.createdAt)
        def doubleRate: Double   = this.rate.toDouble
      }
      private object Response { implicit def responseReads: Reads[Response] = Json.reads[Response] }
      implicit def writes: Writes[MarketBuy] =
        (order: MarketBuy) => Json.obj("market_buy_amount" -> order.amount, OrderTypeKey -> "market_buy")

      def marketBuy(order: MarketBuy) =
        ZIO.fromFuture { implicit ec =>
          request(Json.toJson(order)).map { r =>
            val res = Json.parse(r.body).as[Response]
            ContractedMarketBuy(res.doubleAmount, res.ts, res.doubleRate)
          }
        }
    }
  }
}
