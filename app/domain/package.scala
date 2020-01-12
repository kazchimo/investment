

package object domain {
  case class DomainError(message: String) extends Throwable

  trait Env
}
