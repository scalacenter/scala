package object newtypes {
  // The opaque type definition
  opaque type Logarithm = Double

  // Logarithm$class, ideally all the methods would be static
  object Logarithm {
    // This is the way to lift to the logarithm opaque type
    def apply(d: Double): Logarithm = d
    def safe(d: Double): Option[Logarithm] =
      if (d > 0.0) Some(d) else None

    // here you would define all the extension methods (0 or more)
    implicit class LogarithmOps(val `this`: Logarithm) extends AnyVal {
      // This is the way to unlift the logarithm opaque type
      def toDouble: Double = `this`
      def plus(that: Logarithm): Logarithm = `this` + that
      def times(that: Logarithm): Logarithm = `this` + that
    }
  }
}

object Test {
  def main(args: Array[String]): Unit = {
  }
}

/*package object UseSite {
  val fakeLogarithm: Logarithm = 1.0 // this fails
  val legitLogarithm = Logarithm(1.0) // this works
  val fakeDouble: Double = legitLogarithm // this fails
  val legitDouble: Double = legitLogarithm.toDouble // this works
  legitLogarithm.plus(Logarithm(2.0)) // this works
  legitLogarithm.times(Logarithm(3.0)) // this works
}*/
