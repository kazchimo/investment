package domain.financialProduct

sealed trait CryptoCurrency extends FinancialProduct

case class BitCoin() extends CryptoCurrency { override val code: String = "btc" }
