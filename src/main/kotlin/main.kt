package ws

import kotlinx.css.*
import kotlinx.css.properties.*
import react.dom.render
import styled.StyledComponents
import styled.injectGlobal
import ws.kpres.Move
import ws.kpres.presentation
import ws.slides.*
import kotlin.browser.document


fun CSSBuilder.globalCSS() {
    body {
        backgroundImage = Image("linear-gradient(to bottom right, #E8441F, #921F81)")
        fontFamily = "Picon"
        color = Color.white
        margin(0.em)
        padding(0.em)

        div {
            +"pres-container" {
                backgroundColor = Color("rgba(0, 0, 0, 0.8)")
                transition(::background, 500.ms)
            }
        }
    }

    pre {
        +"code" {
            textAlign = TextAlign.left
            backgroundColor = Color("#2b2b2b")
            alignSelf = Align.stretch
            margin(0.em, 2.em)
            padding(0.5.em)
            borderRadius = 0.2.em
            boxShadow(Color.black, blurRadius = 0.5.em)
            code {
                fontFamily = "fira code"
                fontSize = 0.65.em
                lineHeight = LineHeight("1.2")
            }
        }
    }

    ul {
        listStyleType = ListStyleType.none
        textAlign = TextAlign.left
    }
}

fun main() {
    render(document.getElementById("app")) {

        StyledComponents.injectGlobal { globalCSS() }

        presentation(
                defaultTransition = Move
        ) {

            intro()
            kodeinKoders()
            kodeinFramework()
            noSQL()
            SQLite()
            simpleUsage()
            opinion()
            kodeinDB()
            layers()
            contract()
            immutability()
            open()
            path()
            mppModel()
            query()

            // Find options ?
            // Composite
            // reactive
            // polymorphism & type table
            // migration
            // middleware
            // release
            // future
        }
    }
}
