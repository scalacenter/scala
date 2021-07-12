// here we test unpickling a sealed child in another tasty file
package tastytest.dottyi3149

import tastytest._

object Testdotty_i3149 {
  compiletimeHasNestedChildren[Foo](
    "tastytest.dottyi3149.Foo.Bar",
    "tastytest.dottyi3149.Foo.tastytest$dottyi3149$Foo$$localSealedChildProxy", // workaround to represent "tastytest.dottyi3149.Test.Bar$1",
    "tastytest.dottyi3149.Test.O.Bar",
    "tastytest.dottyi3149.Test.C.Bar"
  )
}
