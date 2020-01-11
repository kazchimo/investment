package domain.order

import domain.TS.TS

/** order marker trait */
trait Order
trait UncontractedOrder extends Order
trait ContractedOrder extends Order {
  val createdAt: TS
  val rate: Double
}

case class MarketBuy(amount: Double)                                        extends UncontractedOrder
case class ContractedMarketBuy(amount: Double, createdAt: TS, rate: Double) extends ContractedOrder
