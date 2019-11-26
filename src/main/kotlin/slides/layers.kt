package ws.slides

import kotlinx.css.*
import kotlinx.css.properties.*
import react.dom.*
import styled.*
import ws.comp.logo
import ws.kpres.PresentationBuilder
import ws.kpres.SlideInfos


private val slideStyle: CSSBuilder.(Int) -> Unit = { state ->
    div {
        +"root" {
            fontSize = 0.88.em
            +"vert" {
                flexGrow = 0.0
            }
        }

        +"vert" {
            display = Display.flex
            flexDirection = FlexDirection.column
            alignSelf = Align.stretch
            flexGrow = 1.0
            flexBasis = FlexBasis.zero
        }

        +"layer" {
            display = Display.flex
            flexDirection = FlexDirection.row
            alignSelf = Align.stretch
        }

        +"entry" {
            padding(0.2.em)
            margin(0.2.em)
            borderRadius = 0.2.em
            flexGrow = 2.0
            flexBasis = FlexBasis.zero
            display = Display.flex
            justifyContent = JustifyContent.center
            alignItems = Align.center
            boxShadow(Color.black, blurRadius = 0.5.em)

            +"small" {
                flexGrow = 1.0
            }

            +"large" {
                flexGrow = 4.0
            }
        }

        +"native" {
            backgroundColor = Color.dimGray
        }

        +"business" {
            backgroundColor = Color("#2281fb")
        }

        +"platform" {
            backgroundColor = Color("#89AB0D")
        }

        span {
            flexGrow = 1.0
            flexBasis = FlexBasis.zero
            margin(0.4.em)
        }

        for (s in 0..7) {
            ".a$s" {
                transition(::opacity, 300.ms)
                opacity = if (state >= s) 1.0 else 0.0
            }
        }

        ".a5" {
            transition("transform", 500.ms)

            if (state >= 8)
                transform {
                    scale(1.4)
                }
        }
    }
}

private val infos = SlideInfos(
        stateCount = 9
)

fun PresentationBuilder.layers() = slide(infos) { props ->

    attrs {
        style = slideStyle
    }

    div("vert root") {
        div("layer a7") {
            div("entry platform") { +"iOS & Native" }
            div("entry platform") { +"Android & Desktop" }
        }
        div("entry large business a6") { +"API" }

        div("layer a5") {
            span {}
            div("entry business") { +"Cache" }
            span {}
        }

        div("layer") {
            div("entry small business a4") { +"KX.Serialization" }
            div("entry business a3") { +"Model" }
            div("entry small business a4") { +"Kryo" }
        }

        div("layer a2") {
            span {}
            div("entry business") { +"Data" }
            span {}
        }

        div("layer a1") {
            span {}
            div("vert i") {
                div("entry native") {
                    +"Kotlin JNI"
                }
                div("entry native") {
                    +"C++ JNI"
                }
            }
            div("entry small native") {
                +"Kotlin/Native"
                br {}
                +"C-Interop"
            }
            span {}
        }

        div("layer") {
            span {}
            div("entry native") { +"Google LevelDB" }
            span {}
        }

    }

}
