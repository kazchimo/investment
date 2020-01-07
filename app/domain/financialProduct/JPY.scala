package domain.financialProduct

case class JPY(override val amount: Double) extends FinancialProduct {
  override val code: String = "jpy"
}
