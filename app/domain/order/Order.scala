package domain.order

import domain.financialProduct.FinancialProduct

/** order marker trait */
case class Order(
    from: FinancialProduct, // product to be sold
    to: FinancialProduct    // product to be bought
) {
  require(from.code != to.code)
}
