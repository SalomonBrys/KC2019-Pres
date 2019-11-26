package ws.slides

import kotlinx.css.opacity
import kotlinx.css.properties.ms
import kotlinx.css.properties.transition
import kotlinx.css.transition
import react.dom.h1
import styled.css
import styled.styledH1
import styled.styledSpan
import ws.kpres.PresentationBuilder
import ws.kpres.SlideInfos


private val infos = SlideInfos(
        stateCount = 3
)

fun PresentationBuilder.noSQL() = slide(infos) { props ->
    h1 {
        styledSpan {
            key = "1"
            css {
                if (props.state >= 1) opacity = 0.1
                transition(::opacity, 300.ms)
            }
            +"Embedded "
        }
        styledSpan {
            key = "2"
            css {
                if (props.state >= 2) opacity = 0.1
                transition(::opacity, 300.ms)
            }
            +"No"
        }
        +"SQL"
        styledSpan {
            key = "3"
            css {
                if (props.state >= 1) opacity = 0.1
                transition(::opacity, 300.ms)
            }
            +" data persistence, everywhere!"
        }
    }
}
