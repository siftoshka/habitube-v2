package az.siftoshka.habitube.presentation.util

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween

/**
 * Animated Navigation.
 */
object Animation {
    fun slideInHorizontally(value: Int): EnterTransition {
        return slideInHorizontally(
            initialOffsetX = { value },
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeIn(animationSpec = tween(300))
    }

    fun slideOutHorizontally(value: Int): ExitTransition {
        return slideOutHorizontally(
            targetOffsetX = { value },
            animationSpec = tween(300, easing = FastOutSlowInEasing)
        ) + fadeOut(animationSpec = tween(300))
    }
}