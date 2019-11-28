package ws.slides

import kotlinx.css.*
import kotlinx.css.properties.TextDecoration
import react.dom.br
import react.dom.h1
import react.dom.h2
import styled.*
import ws.kpres.PresentationBuilder
import ws.kpres.SlideInfos


private val infos = SlideInfos(
)

fun PresentationBuilder.thanks() = slide(infos) { props ->

    attrs.style = {
        backgroundImage = Image("url('images/bird.png')")
        backgroundRepeat = BackgroundRepeat.noRepeat
        backgroundPosition = "right"
        backgroundSize = "contain"

        ".inner-slide" {
            alignItems = Align.flexStart
        }
    }

    h1 { +"Thank you!" }
    styledH2 {
        css {
            color = Color("#007bfa")
            marginTop = 0.em
            textAlign = TextAlign.left
        }
        +"And remember"
        br {}
        +"to VOTE!"
    }

    styledH3 {
        css {
            fontSize = 0.8.em
            textAlign = TextAlign.left
        }
        +"Salomon BRYS"
        styledSpan {
            css {
                fontWeight = FontWeight.w400
            }
            br {}
            styledA(href = "https://twitter.com/salomonbrys") {
                css {
                    color = Color("#007bfa")
                    textDecoration = TextDecoration.none
                }
                +" @salomonbrys"
            }
            br {}
            +"#KotlinConf"
        }
    }

    styledA(href = "https://github.com/Kodein-Framework/Kodein-DB") {
        css {
            fontSize = 0.65.em
            color = Color("#007bfa")
            textDecoration = TextDecoration.none
        }
        +"github.com/Kodein-Framework/Kodein-DB"
    }

}
