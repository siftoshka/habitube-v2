package az.siftoshka.habitube.presentation.util

import androidx.compose.animation.*
import androidx.compose.animation.core.tween

/**
 * Scroll Animation.
 */
object ScrollAnimation {

    @OptIn(ExperimentalAnimationApi::class)
    operator fun invoke(): ContentTransform {
        return slideInVertically(
            initialOffsetY = { 50 },
            animationSpec = tween()
        ) + fadeIn() with slideOutVertically(
            targetOffsetY = { -50 },
            animationSpec = tween()
        ) + fadeOut()
    }
}