package domain.order
import domain.financialProduct.FinancialProduct

case class CoinCheckOrder(override val from: FinancialProduct, override val to: FinancialProduct) extends Order
