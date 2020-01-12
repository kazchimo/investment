package application.useCase

import domain.order.MarketBuy
import infra.exchange.CoinCheckExchange
import javax.inject.Inject

class CoinCheckUseCase @Inject()(exchange: CoinCheckExchange) {

  def marketBuy(amount: Double)  = {
    val order = MarketBuy(amount)
    exchange.marketBuy(order)
  }
}
