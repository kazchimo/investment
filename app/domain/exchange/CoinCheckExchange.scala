package domain.exchange

import domain.financialProduct.{BitCoin, JPY}

object CoinCheckExchange extends Exchange {
  override val availableProducts: Set[Product] = Set(BitCoin, JPY)
}
