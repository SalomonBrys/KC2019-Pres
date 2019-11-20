package ws.slides

import kotlinx.css.FontWeight
import kotlinx.css.em
import kotlinx.css.fontWeight
import kotlinx.css.padding
import react.dom.h1
import styled.css
import styled.styledP
import ws.kpres.PresentationBuilder


fun PresentationBuilder.intro() = slide {
    h1 {
        +"Embedded NoSQL data persistence, everywhere!"
    }
    styledP {
        css {
            fontWeight = FontWeight.w200
            padding(1.em)
        }
        +"Salomon BRYS"
    }
}
