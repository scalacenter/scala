package tastytest

object TestAnnotated {
  def test1 = new Annotated {}
  def test2 = new RootAnnotated {}
  def test3 = {
    val o = new OuterAnnotated {}
    o.foo
  }
}
