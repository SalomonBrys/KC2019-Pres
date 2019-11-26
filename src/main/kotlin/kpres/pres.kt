package ws.kpres

import kotlinext.js.jsObject
import kotlinx.css.*
import kotlinx.html.classes
import kotlinx.html.tabIndex
import org.w3c.dom.HTMLDivElement
import react.*
import react.dom.h1
import react.router.dom.hashRouter
import react.router.dom.route
import styled.css
import styled.styledDiv
import ws.utils.getValue
import ws.utils.provideDelegate
import kotlin.browser.window


typealias SlideHandler = RElementBuilder<SlideProps>.(SlideContentProps) -> Unit

data class SlideInfos(
        val stateCount: Int = 1,
        val containerStyle: CSSBuilder.(Int) -> Unit = {},
        val inTransitions: Transition.Set? = null,
        val outTransitions: Transition.Set? = null,
        val inTransitionDuration: Int? = null,
        val debugAlign: Boolean = false
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
        val defaultTransitions: Transition.Set,
        val defaultTransitionDuration: Int
) : RProps

private data class SlidePosition(val slide: Int, val state: Int)

sealed class TransitionState {
    abstract val forward: Boolean
    class Prepare(override val forward: Boolean) : TransitionState()
    class Execute(val state: Int, val duration: Int, override val forward: Boolean, val remaining: Int) : TransitionState()
}

private fun useTransitionState(getTransitionDuration: (Boolean) -> Int, getTransition: (Boolean) -> Transition): Pair<TransitionState?, (Boolean) -> Unit> {
    var transitionState by useState<TransitionState?>(null)

    useEffectWithCleanup(listOf(transitionState)) {
        val state = transitionState
        val timerId = when (state) {
            is TransitionState.Prepare -> {
                val transitionDuration = getTransitionDuration(state.forward)
                val stateDuration = getTransition(state.forward).stateDuration(transitionDuration, 0).takeIf { it in 0..transitionDuration } ?: transitionDuration
                window.setTimeout({ transitionState = TransitionState.Execute(0, stateDuration, state.forward, transitionDuration - stateDuration) }, 1)
            }
            is TransitionState.Execute -> {
                when (state.remaining) {
                    0 -> window.setTimeout({ transitionState = null }, state.duration)
                    else -> {
                        val stateDuration = getTransition(state.forward).stateDuration(state.remaining, state.state + 1).takeIf { it in 0..state.remaining } ?: state.remaining
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

private fun RBuilder.slideContainer(position: SlidePosition, transition: Transition?, transitionState: TransitionState?, props: PresentationProps) {
    styledDiv {
        if (position.slide != -1) key = "slide-${position.slide}"

        css {
            width = 100.pct
            height = 100.pct
            this.position = Position.absolute
            top = 0.px
            left = 0.px

            if (transition != null) {
                when (transitionState) {
                    is TransitionState.Prepare -> {
                        with(transition) { prepare(transitionState.forward) }
                    }
                    is TransitionState.Execute -> {
                        with(transition) { execute(transitionState.state, transitionState.duration, transitionState.forward) }
                    }
                }
            }
        }

        val slidePair = position.slide.takeIf { it >= 0 } ?.let { props.slides[it] }
        if (slidePair != null) {
            val (slideInfos, slideHandler) = slidePair
            child(functionalComponent = Slide, props = jsObject { this.state = position.state }) {
                val shouldAnim = transitionState == null
                        || (transitionState.forward && position.state != 0)
                        || (!transitionState.forward && position.state != slideInfos.stateCount - 1)
                slideHandler(SlideContentProps(position.state, shouldAnim))
            }
        }
    }
}

private fun PresentationProps.slideInfos(index: SlidePosition) = index.slide.takeIf { it in 0..slides.lastIndex } ?.let { slides[it].first }
private fun PresentationProps.transitionSet(index: SlidePosition, select: SlideInfos.() -> Transition.Set?) = slideInfos(index)?.select() ?: defaultTransitions

private val Presentation by functionalComponent<PresentationProps> { props ->
    val containerDiv = useRef<HTMLDivElement?>(null)
    useEffect(emptyList()) {
        containerDiv.current!!.focus()
    }

    var currentPosition by useState(SlidePosition(-1, 0))
    var previousPosition by useState(SlidePosition(-1, 0))

    val getTransitionDuration = { forward: Boolean -> (if (forward) props.slideInfos(currentPosition) else props.slideInfos(previousPosition))?.inTransitionDuration ?: props.defaultTransitionDuration }
    val (appearState, startAppear) = useTransitionState(getTransitionDuration) { props.transitionSet(currentPosition) { if (it) inTransitions else outTransitions }.appear }
    val (disappearState, startDisappear) = useTransitionState(getTransitionDuration) { props.transitionSet(previousPosition) { if (it) outTransitions else inTransitions }.disappear }

    useEffect(listOf(props.slide, props.state)) {
        if (currentPosition.slide != props.slide) {
            val forward = props.slide > currentPosition.slide
            startAppear(forward)
            startDisappear(forward)
            previousPosition = currentPosition
        }
        currentPosition = SlidePosition(props.slide, props.state)
    }

    useEffect {
        containerDiv.current!!.onkeydown = effect@{
            if (currentPosition.slide < 0) return@effect Unit
            when (it.keyCode) {
                13, 32, 34, 39, 40 -> {
                    if (props.state < (props.slides[currentPosition.slide].first.stateCount - 1)) {
                        window.location.href = "#/${props.slide}/${props.state + 1}"
                    }
                    else if (props.slide < props.slides.lastIndex) {
                        window.location.href = "#/${props.slide + 1}/0"
                    }
                }
                8, 33, 37, 38 -> {
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
        attrs.classes = setOf("pres-container")
        css {
            width = 100.pct
            height = 100.pct
            position = Position.relative
            outline = Outline.none
            overflow = Overflow.hidden

            props.slideInfos(currentPosition)?.containerStyle?.let {
                specific {
                    it(currentPosition.state)
                }
            }
        }
        attrs.tabIndex = "0"

        if (props.slideInfos(currentPosition)?.debugAlign == true || disappearState != null) {
            val transitionSet = props.transitionSet(previousPosition) { if (disappearState?.forward == true) outTransitions else inTransitions }
            slideContainer(previousPosition, transitionSet.disappear, disappearState, props)
        }

        val transitionSet = props.transitionSet(currentPosition) { if (appearState?.forward == true) inTransitions else outTransitions }
        slideContainer(currentPosition, transitionSet.appear, appearState, props)
    }
}

interface PresentationBuilder {
    fun slide(infos: SlideInfos = SlideInfos(), handler: SlideHandler)
}

fun RBuilder.presentation(
        defaultTransition: Transition.Set = Fade,
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
            child(functionalComponent = Presentation, props = PresentationProps(slides, slide, state, defaultTransition, transitionDuration))
        }
    }
}