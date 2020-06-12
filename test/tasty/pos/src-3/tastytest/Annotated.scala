package tastytest

@symbolicAnnot(new tastytest_>>>.Member)
trait Annotated

@rootAnnot(1)
trait RootAnnotated

trait OuterAnnotated extends OuterTrait {
  @innerAnnot(new Inner)
  def foo = 1
}
