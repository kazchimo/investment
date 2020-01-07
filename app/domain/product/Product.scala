package domain.product

/** financial product's marker trait */
trait Product {
  // represent product identity
  val code: String
  val quantity: Double
}
