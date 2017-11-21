import scala.language.implicitConversions

object nullable {
  opaque type Nullable[A <: AnyRef] = A

  object Nullable {
    private implicit def toNullable[A <: AnyRef](a: A): Nullable[A] = a.asInstanceOf[Nullable[A]]
    private implicit def fromNullable[A <: AnyRef](a: Nullable[A]): A = a.asInstanceOf[A]

    def apply[A <: AnyRef](a: A): Nullable[A] = a

    implicit class NullableOps[A <: AnyRef](na: Nullable[A]) {
      def exists(p: A => Boolean): Boolean =
        na != null && p(na)
      def filter(p: A => Boolean): Nullable[A] =
        if (na != null && p(na)) na else null
      def flatMap[B <: AnyRef](f: A => Nullable[B]): Nullable[B] =
        if (na == null) null else f(na)
      def forall(p: A => Boolean): Boolean =
        na == null || p(na)
      def getOrElse(a: => A): A =
        if (na == null) a else na
      def map[B <: AnyRef](f: A => B): Nullable[B] =
        if (na == null) null else f(na)
      def orElse(na2: => Nullable[A]): Nullable[A] =
        if (na == null) na2 else na
      def toOption: Option[A] =
        Option(na)
    }
  }

  def run(): Unit = {
    import Nullable._
    val n1 = Nullable[String](null)
    val n2 = Nullable[String](sys.props("user.di"))
    n1.map((value: String) => "")
    n2.flatMap((_: String) => n1)
    val n3 = n2.orElse(Nullable(sys.props("user.dir")))
    val n4: Option[String] = n3.toOption
  }
}
