package domain.financialProduct

case class JPY(override val quantity: Double) extends FinancialProduct {
  override val code: String = "jpy"
}
