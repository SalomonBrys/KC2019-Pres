package ws.slides

import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.dom.br
import react.dom.h1
import styled.*
import ws.kpres.PresentationBuilder


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
            textAlign = TextAlign.left
            fontWeight = FontWeight.w400
            width = 28.5.em
            marginTop = 2.em
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
    }
}
