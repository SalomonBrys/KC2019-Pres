//package ws.spectacle
//
//import kotlinx.css.CSSBuilder
//import react.RElementBuilder
//import ws.utils.toObject
//
//fun <P : WithStyle> RElementBuilder<P>.style(builder: CSSBuilder.() -> Unit) {
//    attrs.style = CSSBuilder().apply(builder).toObject().unsafeCast<CSSProperties>()
//}
