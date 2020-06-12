package tastytest

object TestSuperOps extends Suite("TestSuperOps") {

  sealed trait Collection
  object Collection {
    final class Set extends Collection with SuperOps
  }

  test(assert(new Collection.Set().foo === 23))

}
