package domain.financialProduct

sealed trait Currency extends FinancialProduct

case class JPY() extends Currency { override val code: String = "jpy" }
