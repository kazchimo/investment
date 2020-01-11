package application.useCase

import domain.order.{ContractedOrder, MarketBuy}
import infra.exchange.CoinCheckExchange
import zio.Task

class CoinCheckUseCase {
  def marketBuy(exchange: CoinCheckExchange, amount: Double): Task[ContractedOrder] = {
    val order = MarketBuy(amount)
    exchange.submit(order)
  }
}
