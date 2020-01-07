package domain.exchange

/** exchange marker trait */
trait Exchange {
  val availableProducts : Set[Product]
}
