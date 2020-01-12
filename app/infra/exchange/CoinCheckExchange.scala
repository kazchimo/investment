package infra.exchange

import com.google.inject.Inject
import domain.TS.TS
import domain.exchange.Exchange
import domain.order.{ContractedMarketBuy, MarketBuy}
import libs.SHA256HMAC
import org.joda.time.DateTime
import play.api.http.{ContentTypes, HeaderNames}
import play.api.libs.json.{JsValue, Json, Reads, Writes}
import play.api.libs.ws.WSClient
import zio.ZIO

class CoinCheckExchange @Inject()(ws: WSClient) extends Exchange with HeaderNames with ContentTypes {
  def marketBuy(order: MarketBuy) = Orders.marketBuy(order)

  private val OrderTypeKey = "order_type"

  private object HttpHandler {
    private val RouteUrl    = "https://coincheck.com"
    private def nonce: Long = DateTime.now().getMillis
    // TODO: envからの取得が失敗した場合をハンドリング
    private val Secret      = sys.env("CoinCheckSecretKey")
    private val AccessKey   = sys.env("CoinCheckAccessKey")
    private def signature(nonce: Long, url: String, body: String) =
      SHA256HMAC.generateHMAC(nonce.toString + url + body, Secret)

    def post(url: String, body: JsValue) = {
      val fullUrl = RouteUrl + url
      ws.url(fullUrl)
        .withHttpHeaders(
          "ACCESS-KEY"       -> AccessKey,
          "ACCESS-NONCE"     -> nonce.toString,
          "ACCESS-SIGNATURE" -> signature(nonce, fullUrl, Json.stringify(body)),
          ACCEPT             -> JSON
        )
        .post(body)
    }
  }

  private object Orders {
    private val url = "/api/exchange/orders"
    private def request(body: JsValue) = HttpHandler.post(url, body)

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
