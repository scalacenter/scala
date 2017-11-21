package scala.tools.nsc.transform

import scala.collection.mutable
import scala.reflect.NameTransformer

abstract class OpaqueTypes extends InfoTransform with TypingTransformers {
  val phaseName: String = "opaquetypes"

  def isOpaqueSymbol(sym: global.Symbol): Boolean =
    sym.name.endsWith(NameTransformer.OPAQUE_PROXY_STRING)

  override def transformInfo(sym: global.Symbol, tpe: global.Type): global.Type = {
    val targetSymbol = sym.info.typeSymbol
    if (sym.isOpaque) getUnderlying(targetSymbol)
    else if (isOpaqueSymbol(targetSymbol)) getUnderlying(targetSymbol)
    else {
      val opaqueSymbols = new mutable.ArrayBuffer[global.Symbol]()
      val opaqueTypes = new mutable.ArrayBuffer[global.Type]()
      tpe.foreach { (t: global.Type) =>
        val symbol = t.typeSymbol
        if (isOpaqueSymbol(symbol)) {
          opaqueSymbols.+=(symbol)
          opaqueTypes.+=(getUnderlying(symbol))
        } else ()
      }
      val newType = tpe.subst(opaqueSymbols.toList, opaqueTypes.toList)
      newType
    }
  }



  final def getUnderlying(sym: global.Symbol): global.Type = sym.annotations.head.tpe
  final def replaceByUnderlying(sym: global.Symbol): global.Type = {
    val underlying = getUnderlying(sym)
    println(s"Replacing type of symbol $sym by $underlying")
    underlying
  }

  protected override def newTransformer(unit: global.CompilationUnit): global.Transformer =
    new OpaqueTypesTransformer(unit)
  final class OpaqueTypesTransformer(unit: global.CompilationUnit) extends TypingTransformer(unit) {
    override def transform(tree: global.Tree): global.Tree = tree match {
      case tt: global.TypeTree if isOpaqueSymbol(tt.symbol) =>
        global.TypeTree(afterOwnPhase(tt.symbol.info))
      case _ => super.transform(tree)
    }
  }
}
