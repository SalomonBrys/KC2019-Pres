package ws.kpres

import kotlinext.js.js
import kotlinx.css.*
import kotlinx.html.tabIndex
import org.w3c.dom.HTMLDivElement
import react.*
import react.dom.h1
import react.router.dom.hashRouter
import react.router.dom.route
import styled.StyleSheet
import styled.css
import styled.getClassName
import styled.styledDiv
import ws.utils.getValue
import ws.utils.provideDelegate
import kotlin.browser.document
import kotlin.browser.window


typealias SlideHandler = RElementBuilder<SlideProps>.(Int) -> Unit

data class SlideInfos(
        val stateCount: Int = 1,
        val bodyStyle: CSSBuilder.() -> Unit = {},
//        val appearTransition: Transition? = null,
//        val disappearTransition: Transition? = null
) {
    init {
        require(stateCount >= 1)
    }
}

private interface RouteProps: RProps {
    val slide: String?
    val state: String?
}

private class PresentationProps(
        val slides: List<Pair<SlideInfos, SlideHandler>>,
        val slide: Int,
        val state: Int,
        val appearTransition: Transition,
        val disappearTransition: Transition,
        val transitionDuration: Int
) : RProps

private data class SlidePosition(val slide: Int, val state: Int)

sealed class TransitionState {
    class Prepare(val forward: Boolean) : TransitionState()
    class Execute(val state: Int, val duration: Int, val forward: Boolean, val remaining: Int) : TransitionState()
}

private fun useTransitionState(transition: Transition, transitionDuration: Int): Pair<TransitionState?, (Boolean) -> Unit> {
    var transitionState by useState<TransitionState?>(null)

    useEffectWithCleanup(listOf(transitionState)) {
        val state = transitionState
        val timerId = when (state) {
            is TransitionState.Prepare -> {
                val stateDuration = transition.stateDuration(transitionDuration, 0).takeIf { it in 0..transitionDuration } ?: transitionDuration
                window.setTimeout({ transitionState = TransitionState.Execute(0, stateDuration, state.forward, transitionDuration - stateDuration) }, 1)
            }
            is TransitionState.Execute -> {
                when (state.remaining) {
                    0 -> window.setTimeout({ transitionState = null }, state.duration)
                    else -> {
                        val stateDuration = transition.stateDuration(state.remaining, state.state + 1).takeIf { it in 0..state.remaining } ?: state.remaining
                        window.setTimeout({ transitionState = TransitionState.Execute(state.state + 1, stateDuration, state.forward, state.remaining - stateDuration) }, state.duration)
                    }
                }
            }
            null -> null
        }
        if (timerId != null) ({ window.clearTimeout(timerId) })
        else ({})
    }

    return transitionState to { forward: Boolean -> transitionState = TransitionState.Prepare(forward) }
}

private fun RBuilder.slideContainer(position: SlidePosition, transition: Transition, transitionState: TransitionState?, getSlide: (Int) -> Pair<SlideInfos, SlideHandler>) {
    styledDiv {
        if (position.slide != -1) key = "slide-${position.slide}"
        css {
            width = 100.pct
            height = 100.pct
            this.position = Position.absolute
            top = 0.px
            left = 0.px

            when (transitionState) {
                is TransitionState.Prepare -> {
                    with(transition) { prepare(transitionState.forward) }
                }
                is TransitionState.Execute -> {
                    with(transition) { execute(transitionState.state, transitionState.duration, transitionState.forward) }
                }
            }
        }

        if (position.slide != -1) {
            val (_, slideHandler) = getSlide(position.slide)
            child(functionalComponent = Slide, props = js {}) {
                slideHandler(position.state)
            }
        }
    }
}

private val Presentation by functionalComponent<PresentationProps> { props ->
    val containerDiv = useRef<HTMLDivElement?>(null)
    useEffect(emptyList()) {
        containerDiv.current!!.focus()
    }

    var currentPosition by useState(SlidePosition(-1, 0))
    var previousPosition by useState(SlidePosition(-1, 0))
    val (appearState, startAppear) = useTransitionState(props.appearTransition, props.transitionDuration)
    val (disappearState, startDisappear) = useTransitionState(props.disappearTransition, props.transitionDuration)

    useEffect(listOf(props.slide, props.state)) {
        if (currentPosition.slide != props.slide) {
            val forward = props.slide > currentPosition.slide
            startAppear(forward)
            startDisappear(forward)
            previousPosition = currentPosition
        }
        currentPosition = SlidePosition(props.slide, props.state)
    }

    useEffectWithCleanup(listOf(currentPosition.slide)) effect@ {
        if (currentPosition.slide == -1) return@effect ({})
        val (slideProps) = props.slides[currentPosition.slide]
        val sheet = object : StyleSheet("slide-${currentPosition.slide}", true) {
            val style by css(builder = slideProps.bodyStyle)
        }
        sheet.inject()
        val className = sheet.getClassName { it::style }
        document.body!!.classList.add(className)
        ({ document.body!!.classList.remove(className) })
    }

    useEffect {
        containerDiv.current!!.onkeydown = effect@{
            if (currentPosition.slide < 0) return@effect Unit
            when (it.keyCode) {
                13, 32, 39, 40 -> {
                    if (props.state < (props.slides[currentPosition.slide].first.stateCount - 1)) {
                        window.location.href = "#/${props.slide}/${props.state + 1}"
                    }
                    else if (props.slide < props.slides.lastIndex) {
                        window.location.href = "#/${props.slide + 1}/0"
                    }
                }
                8, 37, 38 -> {
                    if (props.state > 0) {
                        window.location.href = "#/${props.slide}/${props.state - 1}"
                    }
                    else if (props.slide > 0) {
                        val (prev, _) = props.slides[props.slide - 1]
                        window.location.href = "#/${props.slide - 1}/${prev.stateCount - 1}"
                    }
                }
            }
        }
    }

    styledDiv {
        ref = containerDiv
        css {
            width = 100.pct
            height = 100.pct
            position = Position.relative
            outline = Outline.none
            overflow = Overflow.hidden
        }
        attrs.tabIndex = "0"

        if (disappearState != null) {
            slideContainer(previousPosition, props.disappearTransition, disappearState) { props.slides[it] }
        }

        slideContainer(currentPosition, props.appearTransition, appearState) { props.slides[it] }
    }
}

interface PresentationBuilder {
    fun slide(infos: SlideInfos = SlideInfos(), handler: SlideHandler)
}

fun RBuilder.presentation(
        appearTransition: Transition = FadeIn,
        disappearTransition: Transition = FadeOut,
        transitionDuration: Int = 500,
        builder: PresentationBuilder.() -> Unit
) {
    val slides = ArrayList<Pair<SlideInfos, SlideHandler>>()
    object : PresentationBuilder {
        override fun slide(infos: SlideInfos, handler: SlideHandler) {
            slides += infos to handler
        }
    }.builder()

    if (slides.isEmpty()) {
        h1 { +"No slides!" }
        return
    }

    hashRouter {
        route<RouteProps>("/:slide?/:state?") {
            val slide = it.match.params.slide?.toIntOrNull() ?: 0
            val state = it.match.params.state?.toIntOrNull() ?: 0
            child(functionalComponent = Presentation, props = PresentationProps(slides, slide, state, appearTransition, disappearTransition, transitionDuration))
        }
    }
}