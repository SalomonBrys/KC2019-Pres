package ws.slides

import kotlinx.css.*
import kotlinx.css.properties.ms
import kotlinx.css.properties.transition
import react.dom.h1
import react.dom.h2
import react.dom.p
import styled.css
import styled.styledDiv
import styled.styledP
import ws.kpres.PresentationBuilder
import ws.kpres.SlideInfos


private val infos = SlideInfos(
        stateCount = 2
)

fun PresentationBuilder.batchAndSnapshot() = slide(infos) { props ->
    h1 {
        +"Batch & Snapshot:"
    }

    styledDiv {
        css {
            width = 20.em
        }
        p {
            +"A snapshot ensures that the queried data set reflects the state it was when created."
        }

        p {
            +"A batch modifies the database \"atomically\"."
        }

        styledP {
            css {
                transition(::opacity, 300.ms)
                opacity = if (props.state < 1) 0.0 else 1.0
            }
            +"Therefore, a snapshot can never reflect part of a batch modification."
        }
    }
}
