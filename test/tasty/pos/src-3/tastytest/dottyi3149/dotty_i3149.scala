// here we test unpickling a sealed child in another tasty file
package tastytest.dottyi3149

sealed class Foo
object Foo {
  final class Child1 extends Foo
}

class Test {
  def f = {
    class Child2 extends Foo
  }
}
