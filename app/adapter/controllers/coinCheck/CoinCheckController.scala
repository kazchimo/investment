package adapter.controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, ControllerComponents}

class CoinCheckController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {
  def marketBuy = Action.async()
}
