package domain.financialProduct

case class BitCoin(override val quantity: Double) extends FinancialProduct {
  override val code: String = "btc"
}
