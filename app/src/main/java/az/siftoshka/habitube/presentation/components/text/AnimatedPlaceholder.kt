package az.siftoshka.habitube.presentation.components.text

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import az.siftoshka.habitube.domain.util.doWhenHasNextOrPrevious
import az.siftoshka.habitube.presentation.util.ScrollAnimation

/**
 * Composable function of animated placeholder.
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedPlaceholder(hints: Array<String>) {
    val iterator = hints.toList().listIterator()

    val target by produceState(initialValue = hints.first()) {
        iterator.doWhenHasNextOrPrevious {
            value = it
        }
    }

    AnimatedContent(
        targetState = target,
        transitionSpec = { ScrollAnimation() }
    ) { str ->
        Text(
            text = str,
            style = MaterialTheme.typography.body1
        )
    }
}