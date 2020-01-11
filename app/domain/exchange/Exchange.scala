package domain.exchange

import domain.order.{ContractedOrder, UncontractedOrder}
import zio.Task

/** exchange marker trait */
trait Exchange {
  val availableProducts : Set[Product]

  def submit(order: UncontractedOrder): Task[ContractedOrder]
}
