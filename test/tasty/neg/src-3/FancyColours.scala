package tastytest

object FancyColours {
  sealed trait Pretty { self: Colour => }
  sealed trait Dull { self: Colour => }
  // sealed abstract class Colour  {

  // }
  // object Colour {
  //   case object Pink extends Colour with Pretty
  //   case object Violet extends Colour with Pretty
  //   case object Red extends Colour with Dull
  // }
  enum Colour  {
    case Pink extends Colour with Pretty
    case Violet extends Colour with Pretty
    case Red extends Colour with Dull
  }
}
