package ws.slides

import kotlinx.css.*
import kotlinx.css.properties.ms
import kotlinx.css.properties.transition
import styled.css
import styled.styledP
import styled.styledSpan
import ws.comp.logo
import ws.kpres.FadeIn
import ws.kpres.PresentationBuilder
import ws.kpres.SlideInfos


private val infos = SlideInfos(
        stateCount = 2,
        bodyStyle = {
            div {
                +"darker" {
                    backgroundColor = Color("#46AF6D")
                }
            }
        },
        appearTransition = FadeIn
)

fun PresentationBuilder.kodeinDB() = slide(infos) { state ->

    logo(division = "DB", zoom = 0.8 ) {
        styledP {
            css {
                padding(0.em)
                margin(0.em)
            }
            styledSpan {
                css {
                    transition(::opacity, 300.ms)
                    if (state >= 1) opacity = 0.4
                }
                +"painless "
            }
            +"NoSQL"
            styledSpan {
                css {
                    transition(::opacity, 300.ms)
                    if (state >= 1) opacity = 0.4
                }
                +" persistence"
            }
        }
    }


}
