package infra.exchange

import com.google.inject.Inject
import domain.DomainError
import domain.TS.TS
import domain.exchange.Exchange
import domain.financialProduct.{BitCoin, JPY}
import domain.order.{ContractedMarketBuy, ContractedOrder, MarketBuy, UncontractedOrder}
import play.api.http.{ContentTypes, HeaderNames}
import play.api.libs.json.{JsValue, Json, Reads, Writes}
import play.api.libs.ws.WSClient
import zio.{Task, ZIO}

class CoinCheckExchange @Inject()(ws: WSClient) extends Exchange with HeaderNames with ContentTypes {
  override val availableProducts: Set[Product] = Set(BitCoin, JPY)

  override def submit(order: UncontractedOrder): Task[ContractedOrder] = order match {
    case v: MarketBuy => CoinCheckApi.Orders.marketBuy(v)
    case v            => ZIO.fail(DomainError(s"unsupported order type: ${v.getClass.getSimpleName}"))
  }

  object CoinCheckApi {
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
        def doubleAmount = this.amount.toDouble
        def ts           = TS.fromString(this.createdAt)
        def doubleRate   = this.rate.toDouble
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
