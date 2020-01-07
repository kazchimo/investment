package domain.product

case class BitCoin(override val quantity: Double) extends Product {
  override val code: String = "btc"
}
