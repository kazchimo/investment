package domain.product

case class JPY(override val quantity: Double) extends Product {
  override val code: String = "jpy"
}
