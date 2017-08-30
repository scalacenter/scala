/* NSC -- new Scala compiler
 * Copyright 2005-2013 LAMP/EPFL
 * @author  Martin Odersky
 */

package scala.reflect.internal.util

import scala.reflect.internal.{Phase, SymbolTable}

abstract class StatisticsInfo {
  type Global <: SymbolTable
  val symbolTable: SymbolTable

  import symbolTable._
  import statistics.{treeNodeCount, nodeByType}

  val retainedCount  = Statistics.newCounter("#retained tree nodes")
  val retainedByType = Statistics.newByClass("#retained tree nodes by type")(Statistics.newCounter(""))

  def print(phase: Phase, units: Iterator[Global#CompilationUnit]) = {
    inform("*** Cumulative statistics at phase " + phase)

    if (settings.YhotStatistics.value) {
      // High overhead, only enable retained stats under hot stats
      retainedCount.value = 0
      for (c <- retainedByType.keys)
        retainedByType(c).value = 0
      for (u <- units; t <- u.body) {
        retainedCount.value += 1
        retainedByType(t.getClass).value += 1
      }
    }

    val quants =
      if (phase.name == "parser") Seq(treeNodeCount, nodeByType, retainedCount, retainedByType)
      else Statistics.allQuantities

    for (q <- quants if q.showAt(phase.name)) inform(q.line)
  }
}
