package domain.order

/** order marker trait */
trait Order
case class MarketBuy(amount: Double) extends Order
case class MarketSell(amount: Double) extends Order

