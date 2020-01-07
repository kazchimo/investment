package domain.order

sealed trait OrderType
object Buy extends OrderType with Limited with Spot with BuyTrade
object Sell extends OrderType with Limited with Spot with SellTrade
object MarketBuy extends OrderType with CarteBlanche with Spot with BuyTrade
object MarketSell extends OrderType with CarteBlanche with Spot with SellTrade
object LeverageBuy extends OrderType with Limited with Leveraged with BuyTrade
object LeverageSell extends OrderType with Limited with Leveraged with SellTrade


sealed trait Pricing
trait Limited extends Pricing
trait CarteBlanche extends Pricing

sealed trait ProductTransaction
trait Spot extends ProductTransaction
trait Leveraged extends ProductTransaction

sealed trait TradeType
trait BuyTrade extends TradeType
trait SellTrade extends TradeType

