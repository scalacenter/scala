/*import scala.language.higherKinds
import scala.language.implicitConversions

object union {
  opaque type |[A, B] = AnyRef

  object | {
    private implicit def fromUnion[A, B](u: |[A, B]): AnyRef = u.asInstanceOf[AnyRef]
    private implicit def toUnion[A, B](a: AnyRef): |[A, B] = a.asInstanceOf[|[A, B]]

    /** Evidence that `A <: B`, taking `|`-types into account. */
    sealed trait Evidence[-A, +B]

    /** A unique (and typically dead-code-eliminated away) instance of
      * `Evidence`.
      */
    private object ReusableEvidence extends Evidence[scala.Any, scala.Any]

    abstract sealed class EvidenceLowestPrioImplicits {
      this: Evidence.type =>
      /** If `A <: B2`, then `A <: B1 | B2`. */
      implicit def right[A, B1, B2](implicit ev: Evidence[A, B2]): Evidence[A, B1 | B2] =
        ReusableEvidence.asInstanceOf[Evidence[A, B1 | B2]]

      /** Given a covariant type constructor `F[+_]`, if `A <: B`, then
        * `F[A] <: F[B]`.
        */
      implicit def covariant[F[+ _], A, B](implicit ev: Evidence[A, B]): Evidence[F[A], F[B]] =
        ReusableEvidence.asInstanceOf[Evidence[F[A], F[B]]]

      /** Given a contravariant type constructor `F[-_]`, if `B <: A`, then
        * `F[A] <: F[B]`.
        */
      implicit def contravariant[F[- _], A, B](implicit ev: Evidence[B, A]): Evidence[F[A], F[B]] =
        ReusableEvidence.asInstanceOf[Evidence[F[A], F[B]]]
    }

    abstract sealed class EvidenceLowPrioImplicits
      extends EvidenceLowestPrioImplicits {
      this: Evidence.type =>

      /** If `A <: B1`, then `A <: B1 | B2`. */
      implicit def left[A, B1, B2](implicit ev: Evidence[A, B1]): Evidence[A, B1 | B2] =
        ReusableEvidence.asInstanceOf[Evidence[A, B1 | B2]]
    }

    object Evidence extends EvidenceLowPrioImplicits {
      /** `A <: A`. */
      implicit def base[A]: Evidence[A, A] =
        ReusableEvidence.asInstanceOf[Evidence[A, A]]

      /** If `A1 <: B` and `A2 <: B`, then `A1 | A2 <: B`. */
      implicit def allSubtypes[A1, A2, B](
                                           implicit ev1: Evidence[A1, B],
                                           ev2: Evidence[A2, B]): Evidence[A1 | A2, B] =
        ReusableEvidence.asInstanceOf[Evidence[A1 | A2, B]]
    }

    /** Upcast `A` to `B1 | B2`.
      *
      * This needs evidence that `A <: B1 | B2`.
      */
    implicit def from[A, B1, B2](a: A)(implicit ev: Evidence[A, B1 | B2]): B1 | B2 =
      a.asInstanceOf[B1 | B2]

    /** Upcast `F[A]` to `F[B]`.
      *
      * This needs evidence that `F[A] <: F[B]`.
      */
    implicit def fromTypeConstructor[F[_], A, B](a: F[A])(
      implicit ev: Evidence[F[A], F[B]]): F[B] =
      a.asInstanceOf[F[B]]

    /** Operations on union types. */
    implicit class UnionOps[A <: _ | _](val self: A) extends AnyVal {
      /** Explicitly merge a union type to a supertype (which might not be a
        * union type itself).
        *
        * This needs evidence that `A <: B`.
        */
      def merge[B](implicit ev: |.Evidence[A, B]): B =
        self.asInstanceOf[B]
    }
  }

  def run(): Unit = {
    import |._
    val x1: Int | String = 4
  }
}*/

object union {
  def run(): Unit = ()
}
