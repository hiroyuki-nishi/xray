import scala.language.implicitConversions

package object domain {
  trait Value[T] extends Any {
    def value: T
  }

  implicit def wrapValue[P, V <: Value[P]](value: P)(implicit conv: P => V): V = conv(value)
  implicit def unwrapValue[P](value: Value[P]): P                              = value.value
}
