package tastytest

object SelectIns {

  def test1 = {
    val foo = scala.collection.mutable.ArrayBuffer.empty[Seq[Double]]
    val bar = Seq.empty[Double]
    foo.append(bar) // TODO [tasty]: how to ensure Selectin appears perhaps for an annotation?
  }

}
