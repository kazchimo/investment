package domain.order

import domain.financialProduct.FinancialProduct

/** order marker trait */
trait Order {
  require(from.code != to.code)

  val from: FinancialProduct // product to be sold
  val to: FinancialProduct   // product to be bought
}
