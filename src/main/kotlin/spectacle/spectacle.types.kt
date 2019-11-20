//@file:Suppress("unused", "EnumEntryName")
//
//package ws.spectacle
//
//import react.RBuilder
//import react.RProps
//import react.ReactElement
//import react.buildElement
//import react.dom.WithClassName
//
//
//interface StringEnum {
//    val name: String
//}
//
//data class Align(val h: Value, val v: Value) {
//    enum class Value(val flexName: String) {
//        start("flex-start"),
//        center("center"),
//        end("flex-end");
//    }
//}
//
//external interface WithStyle : RProps {
//    var style: CSSProperties
//}
//
//external interface AnimProps : RProps, WithStyle {
//    var fromStyle: CSSProperties
//    var onAnim: ((forwards: Boolean, animIndex: Number) -> Unit)?
//    var order: Number?
//    var route: Any?
//    var toStyle: Array<CSSProperties>
//    var transitionDuration: Number
//}
//
//external interface AppearProps : RProps, WithStyle {
//    var endValue: Any?
//    var fid: String?
//    var order: Number?
//    var startValue: Any?
//    var transitionDuration: Number?
//}
//var AppearProps.easing by SE<Easing>()
//
//external interface BaseProps : RProps, WithStyle, WithClassName {
//    var bgColor: String?
//    var bgDarken: Number?
//    var bgImage: String?
//    var bold: Boolean?
//    var caps: Boolean?
//    var italic: Boolean?
//    var textAlign: String?
//    var textColor: String?
//    var textFont: String?
//    var textSize: String?
//}
//var BaseProps.margin by LD
//var BaseProps.padding by LD
//
//enum class BulletStyle {
//    arrow,
//    classicCheck,
//    cross,
//    greenCheck,
//    star
//}
//
//external interface CodePaneProps : RProps, WithStyle, WithClassName {
//    var contentEditable: Boolean?
//    var lang: String?
//    var source: String?
//}
//val CodePaneProps.theme by SE<PaneTheme>()
//
//external interface ComponentPlaygroundProps : RProps {
//    var code: String?
//    var previewBackgroundColor: String?
//    var scope: Any?
//    var transformCode: ((code: String) -> String)?
//}
//val ComponentPlaygroundProps.theme by SE<PaneTheme>()
//
//external interface CSSProperties
//
//external interface DeckProps : RProps {
//    var autoplay: Boolean?
//    var autoplayDuration: Number?
//    var autoplayLoop: Boolean?
//    var autoplayOnStart: Boolean?
//    var controls: Boolean?
//    var disableKeyboardControls: Boolean?
//    var disableTouchControls: Boolean?
//    var globalStyles: Boolean?
//    var history: Any? // Needs a type, see https://github.com/ReactTraining/history
//    var showFullscreenControl: Boolean?
//    var onStateChange: ((previousState: String?, nextState: String?) -> Unit)?
//    var theme: Theme?
//    var transitionDuration: Number?
//    var contentWidth: String?
//    var contentHeight: String?
//}
//var DeckProps.easing by SE<Easing>()
//var DeckProps.progress by SE<Progress>()
//var DeckProps.transition by SEArray<Transition>()
//
//enum class Easing : StringEnum {
//    back,
//    backIn,
//    backOut,
//    backInOut,
//    bounce,
//    bounceIn,
//    bounceOut,
//    bounceInOut,
//    circle,
//    circleIn,
//    circleOut,
//    circleInOut,
//    linear,
//    linearIn,
//    linearOut,
//    linearInOut,
//    cubic,
//    cubicIn,
//    cubicOut,
//    cubicInOut,
//    elastic,
//    elasticIn,
//    elasticOut,
//    elasticInOut,
//    exp,
//    expIn,
//    expOut,
//    expInOut,
//    poly,
//    polyIn,
//    polyOut,
//    polyInOut,
//    quad,
//    quadIn,
//    quadOut,
//    quadInOut,
//    sin,
//    sinIn,
//    sinOut,
//    sinInOut
//}
//
//external interface FillProps : RProps, WithStyle, WithClassName
//
//external interface FitProps : FillProps
//
//external interface GoToActionProps : RProps, WithStyle
//var GoToActionProps.margin by LD
//var GoToActionProps.padding by LD
//val GoToActionProps.slide by NoS
//var GoToActionProps.render by Serialized<RBuilder.(NoSCB) -> Unit, ((Any) -> Unit) -> ReactElement?>(
//        { builder ->
//            val ret = { cb: (Any) -> Unit -> buildElement { builder(NoSCB(cb)) } }
//            ret.asDynamic().wrapped = builder
//            ret
//        },
//        { it.asDynamic().wrapped as RBuilder.(NoSCB) -> Unit }
//)
//
//external interface HeadingProps : BaseProps {
//    var fit: Boolean?
//    var lineHeight: Number?
//    var size: Number?
//}
//
//external interface ImageProps : WithClassName {
//    var alt: String?
//    var display: String?
//    var src: String?
//}
//var ImageProps.height by LD
//var ImageProps.margin by LD
//var ImageProps.padding by LD
//var ImageProps.width by LD
//
//external interface LayoutProps : RProps, WithStyle
//
//external interface LinkProps : BaseProps {
//    var href: String?
//}
//var LinkProps.target by SE<Target>()
//
//external interface ListProps : BaseProps
//var ListProps.bulletStyle by SE<BulletStyle>()
//
//external interface MarkdownProps : RProps {
//    var mdastConfig: MdastConfig?
//    var source: String?
//}
//
//interface MdastConfig : NumberOrStringDictionary
//
//enum class PaneTheme {
//    dark,
//    light,
//    external
//}
//
//enum class Progress {
//    pacman,
//    bar,
//    number,
//    none
//}
//
//external interface SlideProps : BaseProps {
//    var contentStyles: CSSProperties?
//    var controlColor: String?
//    var dispatch: (() -> Unit)?
//    var progressColor: String?
//    var history: Any?
//    var id: String?
//    var lastSlideIndex: Number?
//    var notes: String?
//    var slideIndex: Number?
//    var state: String?
//    var transitionDuration: Number?
//}
//var SlideProps.align by Serialized<Align, String>(
//        { "${it.h.flexName} ${it.v.flexName}" },
//        { str -> str.split(" ").map { flexName -> Align.Value.values().first { it.flexName == flexName } } .let { Align(it[0], it[1]) } }
//)
//val SlideProps.hash by NoS
//var SlideProps.onActive by Serialized<(NoSIn) -> Unit, (Any) -> Unit>(
//        { cb ->
//            val ret = { index: dynamic -> cb(NoSIn(index)) }
//            ret.asDynamic().wrapped = cb
//            ret
//        },
//        {
//            it.asDynamic().wrapped as (dynamic) -> Unit
//        }
//)
//var SlideProps.transition by SEArray<Transition>()
//var SlideProps.transitionIn by SEArray<Transition>()
//var SlideProps.transitionOut by SEArray<Transition>()
//
//external interface SProps : BaseProps
//var SProps.type by SEArray(SType::type) { type -> SType.values().first { it.type == type } }
//
//enum class SType(val type: String) {
//    italic("italic"),
//    bold("bold"),
//    lineThrough("line-through"),
//    underline("underline")
//}
//
//enum class Target {
//    _blank,
//    _self,
//    _parent,
//    _top
//}
//
//external interface TextProps : BaseProps {
//    var fit: Boolean?
//    var lineHeight: Number?
//}
//
//class Theme
//
//enum class Transition {
//    slide,
//    zoom,
//    fade,
//    spin
//}
