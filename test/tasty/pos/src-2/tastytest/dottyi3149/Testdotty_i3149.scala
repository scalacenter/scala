// here we test unpickling a sealed child in another tasty file
package tastytest.dottyi3149

object Testdotty_i3149 {
  compiletimeHasNestedChildren[Foo](
    "Foo.Child1",
    "Test.Child2$1" // TODO check name in TASTy
  )
}
