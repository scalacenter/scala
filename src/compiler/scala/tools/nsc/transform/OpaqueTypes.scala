package scala.tools.nsc.transform

import scala.reflect.NameTransformer

abstract class OpaqueTypes extends InfoTransform with TypingTransformers {
  val phaseName: String = "opaquetypes"
  //override def keepsTypeParams: Boolean = false

  def isOpaqueSymbol(sym: global.Symbol): Boolean =
    sym.name.endsWith(NameTransformer.OPAQUE_PROXY_STRING)

  override def transformInfo(sym: global.Symbol, tpe: global.Type): global.Type = {
    if (sym.fullName.contains("opaquetypes")) println(s"Transforming $sym")
    val targetSymbol = sym.info.typeSymbol
    if (sym.isOpaque) replaceByUnderlying(targetSymbol, tpe)
    else if (isOpaqueSymbol(targetSymbol)) replaceByUnderlying(targetSymbol, tpe)
    else tpe
  }

  final def getUnderlying(sym: global.Symbol): global.Type = sym.annotations.head.tpe
  final def replaceByUnderlying(sym: global.Symbol, tpe: global.Type): global.Type = {
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
      case m: global.MemberDef =>
        println(s"HAAAA ${m.symbol} ${afterOwnPhase(m.symbol.info)}")
        super.transform(m)
      case _ => super.transform(tree)
    }
  }
}
