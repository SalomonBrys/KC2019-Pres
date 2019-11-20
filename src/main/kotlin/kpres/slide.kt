package ws.kpres

import kotlinx.css.*
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.events.Event
import react.RProps
import react.functionalComponent
import react.useEffectWithCleanup
import react.useRef
import styled.StyleSheet
import styled.css
import styled.getClassName
import styled.styledDiv
import ws.utils.getValue
import kotlin.browser.document
import kotlin.browser.window
import kotlin.math.min


interface SlideProps : RProps {
    var style: (CSSBuilder.() -> Unit)?
}

internal val Slide by functionalComponent<SlideProps> { props ->
    val inner = useRef<HTMLDivElement?>(null)

    useEffectWithCleanup {
        val listener = { _: Event? ->
            val factor = min(window.innerWidth.toDouble() / 1024.0, window.innerHeight.toDouble() / 640.0).takeIf { it > 1.0 } ?: 1.0
            inner.current!!.style.transform = "scale(${factor})"
        }
        listener(null)
        window.addEventListener("resize", listener)
        ({ window.removeEventListener("resize", listener) })
    }

    styledDiv {
        css {
            width = 100.pct
            height = 100.pct
            display = Display.flex
            justifyContent = JustifyContent.center
            alignItems = Align.center

            props.style?.invoke(this)
        }

        styledDiv {
            ref = inner
            css {
                width = (1024.px - 4.em)
                height = (640.px - 4.em)
                padding(2.em)
                fontSize = 2.em
                display = Display.flex
                flexDirection = FlexDirection.column
                justifyContent = JustifyContent.center
                alignItems = Align.center
                alignContent = Align.center
                textAlign = TextAlign.center
                overflow = Overflow.hidden
            }

            props.children()
        }
    }
}
