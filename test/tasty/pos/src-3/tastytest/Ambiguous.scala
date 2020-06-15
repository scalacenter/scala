package tastytest

class Ambiguous[T] {
  class annot(elem: Box[T]) extends scala.annotation.StaticAnnotation {
    def this(elem: T) = this(new Box[T](elem))
  }
}
