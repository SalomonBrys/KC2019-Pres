package ws.kpres

import kotlinx.css.CSSBuilder
import kotlinx.css.opacity
import kotlinx.css.pct
import kotlinx.css.properties.ms
import kotlinx.css.properties.transform as transforms
import kotlinx.css.properties.transition
import kotlinx.css.properties.translateX
import kotlinx.css.properties.translateY
import kotlinx.css.transform


interface Transition {
    fun CSSBuilder.prepare(forward: Boolean)
    fun stateDuration(remaining: Int, state: Int): Int = remaining
    fun CSSBuilder.execute(state: Int, duration: Int, forward: Boolean)
}

object FadeIn : Transition {
    override fun CSSBuilder.prepare(forward: Boolean) {
        opacity = 0.0
    }

    override fun CSSBuilder.execute(state: Int, duration: Int, forward: Boolean) {
        transition(::opacity, duration.ms)
        opacity = 1.0
    }
}

object FadeOut : Transition {
    override fun CSSBuilder.prepare(forward: Boolean) {
        opacity = 1.0
    }

    override fun CSSBuilder.execute(state: Int, duration: Int, forward: Boolean) {
        transition(::opacity, duration.ms)
        opacity = 0.0
    }
}

object MoveIn : Transition {
    override fun CSSBuilder.prepare(forward: Boolean) {
        opacity = 0.0
        transforms { translateX(if (forward) 50.pct else (-50).pct) }
    }

    override fun CSSBuilder.execute(state: Int, duration: Int, forward: Boolean) {
        transition(::transform, duration.ms)
        transition(::opacity, duration.ms)
        transforms {}
        opacity = 1.0
    }
}


object MoveOut : Transition {
    override fun CSSBuilder.prepare(forward: Boolean) {
        opacity = 1.0
        transforms {}
    }

    override fun CSSBuilder.execute(state: Int, duration: Int, forward: Boolean) {
        transition(::transform, duration.ms)
        transition(::opacity, duration.ms)
        transforms { translateX( if (forward) (-50).pct else 50.pct) }
        opacity = 0.0
    }
}
