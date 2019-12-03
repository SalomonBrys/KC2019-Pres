package ws.slides

import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.dom.br
import react.dom.h1
import styled.*
import ws.kpres.PresentationBuilder
import ws.kpres.SlideInfos


private val infos = SlideInfos(
        notes = {
            css {

            }
            +"Hello everyone, thank you for joining me."
        }
)

fun PresentationBuilder.intro() = slide {
    attrs.style = {
        backgroundImage = Image("url('images/KC.png')")
        backgroundRepeat = BackgroundRepeat.noRepeat
        backgroundPosition = "bottom right"
    }

    styledH1 {
        css {
            margin(0.5.em)
        }
        +"Embedded NoSQL data persistence, everywhere!"
    }
    styledH2 {
        css {
            fontWeight = FontWeight.w200
            marginTop = 0.em
        }
        +"Salomon BRYS"
    }

    styledH3 {
        css {
            fontSize = 0.8.em
            fontWeight = FontWeight.w400
            width = 28.5.em
            textAlign = TextAlign.left
            marginTop = 0.5.em
        }
        styledA(href = "https://twitter.com/salomonbrys") {
            css {
                color = Color("#007bfa")
                textDecoration = TextDecoration.none
                display = Display.block
                marginBottom = 0.75.em
            }
            +" @salomonbrys"
        }
        +"Copenhagen"
        br {}
        +"Denmark"

        styledA(href = "https://salomonbrys.github.io/KC2019-Pres") {
            css {
                display = Display.block
                marginTop = 4.em
                fontSize = 0.75.em
                color = Color("#007bfa")
                textDecoration = TextDecoration.none
            }

            +"salomonbrys.github.io/KC2019-Pres"
        }
    }

}
