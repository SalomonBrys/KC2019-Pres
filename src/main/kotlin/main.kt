package ws

import kotlinx.css.*
import kotlinx.css.properties.ms
import kotlinx.css.properties.transition
import react.dom.div
import react.dom.render
import styled.StyledComponents
import styled.injectGlobal
import ws.kpres.MoveIn
import ws.kpres.MoveOut
import ws.kpres.presentation
import ws.slides.intro
import ws.slides.kodeinKoders
import ws.slides.kodeinFramework
import ws.slides.kodeinDB
import kotlin.browser.document


fun CSSBuilder.globalCSS() {
    body {
        backgroundImage = Image("linear-gradient(to bottom right, #E8441F, #921F81)")
        fontFamily = "Picon"
        color = Color.white
        margin(0.em)
        padding(0.em)

        div {
            +"darker" {
                backgroundColor = Color("rgba(0, 0, 0, 0.8)")
                transition(::background, 500.ms)
            }
        }
    }
}

fun main() {
    render(document.getElementById("app")) {

        StyledComponents.injectGlobal { globalCSS() }

        div("darker") {
            presentation(
                    appearTransition = MoveIn,
                    disappearTransition = MoveOut
            ) {
                intro()
                kodeinKoders()
                kodeinFramework()
                kodeinDB()

            }
        }
    }
}
