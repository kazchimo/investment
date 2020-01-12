package adapter.controllers.coinCheck

import application.useCase.CoinCheckUseCase
import domain.Env
import infra.exchange.CoinCheckExchange
import javax.inject.Inject
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc.{AbstractController, ControllerComponents}
import zio.Runtime
import zio.internal.PlatformLive

class CoinCheckController @Inject()(cc: ControllerComponents, ws: WSClient, useCase: CoinCheckUseCase)
    extends AbstractController(cc) {
  object CoinCheckEnv extends CoinCheckExchange(ws) with Env
  val runtime = Runtime(CoinCheckEnv, PlatformLive.Default)

  def marketBuy = Action.async(parse.json[MarketBuyRequest]) { implicit request =>
    runtime.unsafeRun(
      useCase.marketBuy(request.body.amount).map(order => Ok(Json.toJson(order))).toFuture
    )
  }
}
