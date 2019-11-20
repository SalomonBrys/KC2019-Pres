package ws.slides

import kotlinx.css.*
import kotlinx.css.properties.s
import kotlinx.css.properties.transition
import react.dom.img
import react.dom.li
import react.dom.span
import styled.css
import styled.styledImg
import styled.styledUl
import ws.comp.logo
import ws.kpres.FadeOut
import ws.kpres.PresentationBuilder
import ws.kpres.SlideInfos
import ws.utils.li


private fun CSSBuilder.targetsStyle(state: Int) {
    listStyleType = ListStyleType.none
    display = Display.flex
    flexDirection = FlexDirection.row
    padding(0.em, 1.em)
    marginTop = 2.em
    alignSelf = Align.stretch
    position = Position.relative

    li {
        backgroundColor = Color("#EDEDED")
        color = Color("#444444")
        fontSize = 0.75.em
        fontWeight = FontWeight.w500
        padding(0.4.em)
        margin(0.2.em)
        borderRadius = 0.5.em
        flexGrow = 1.0
        flexBasis = FlexBasis.zero
        if (state == 0) opacity = 0.0
        transition(::opacity, duration = 0.3.s)
        transition(::backgroundColor, duration = 0.3.s)

        +"nogo" {
            if (state >= 2) opacity = 0.2

            +"web" {
                if (state >= 3) opacity = 0.75
                span {
                    opacity = if (state >= 3) 1.0 else 0.0
                    transition(::opacity, duration = 0.3.s)
                }
            }
        }
    }

    img {
        +"cross" {
            width = 3.em
            position = Position.absolute
            left = (69.pct - 1.5.em)
            top = (-0.4).em
            opacity = if (state >= 4) 1.0 else 0.0
            transition(::opacity, duration = 0.3.s)
        }
    }
}

private val infos = SlideInfos(
        stateCount = 5,
        bodyStyle = {
            div {
                +"darker" {
                    backgroundColor = Color("#46AF6D")
                }
            }
        },
        disappearTransition = FadeOut
)

fun PresentationBuilder.kodeinFramework() = slide(infos) { state ->
    logo(division = "Framework", href = "https://kodein.org", zoom = 0.8 ) {
        +"painless "
        styledImg(src = "images/kotlin-white.svg") {
            css { height = 0.65.em }
        }
        +" kotlin"
    }

    styledUl {
        css { targetsStyle(state) }

        li { +"Android" }
        li { +"iOS" }
        li("nogo web") {
            +"Web"
            span {
                +"?"
            }
        }
        li("nogo server") {
            +"Server"
        }
        li { +"Desktop" }
        img(src = "images/cross.png", classes = "cross") {}
    }
}
