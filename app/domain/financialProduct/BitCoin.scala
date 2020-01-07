package domain.financialProduct

case class BitCoin(override val amount: Double) extends FinancialProduct {
  override val code: String = "btc"
}
