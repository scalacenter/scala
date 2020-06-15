package tastytest

@symbolicAnnot(new tastytest_>>>.Member)
trait Annotated

@rootAnnot(1)
trait RootAnnotated

trait OuterAnnotated extends OuterTrait {
  @innerAnnot(new Inner)
  def foo = 1
}

object SelectInAnnotated {

  val e = new Box[Double](0)

  val ambig = new Ambiguous[Box[Double]]

  @ambig.annot(e)
  trait AmbiguousAnnotated

}
