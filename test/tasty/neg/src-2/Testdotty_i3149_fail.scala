// here we test unpickling a sealed child in another tasty file
package tastytest.dottyi3149

import tastytest._

object Testdotty_i3149 {
  def foo(f: Foo): Unit = f match {
    case f: Foo.Bar => ()
  }
}
