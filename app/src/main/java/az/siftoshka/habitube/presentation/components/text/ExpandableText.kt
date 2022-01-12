package az.siftoshka.habitube.presentation.components.text

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.theme.spacing

@Composable
fun ExpandableText(modifier: Modifier = Modifier, text: String) {
    var isExpanded by remember { mutableStateOf(false) }
    val textLayoutResultState = remember { mutableStateOf<TextLayoutResult?>(null) }
    var isClickable by remember { mutableStateOf(false) }
    var finalText by remember { mutableStateOf(text) }
    val context = LocalContext.current

    val textLayoutResult = textLayoutResultState.value
    LaunchedEffect(textLayoutResult) {
        if (textLayoutResult == null) return@LaunchedEffect

        when {
            isExpanded -> {
                finalText = "$text ...${context.getString(R.string.text_show_less)}"
            }
            !isExpanded && textLayoutResult.hasVisualOverflow -> {
                val lastCharIndex = textLayoutResult.getLineEnd(3 - 1)
                val showMoreString = " ...${context.getString(R.string.text_show_more)}"
                val adjustedText = text
                    .substring(startIndex = 0, endIndex = lastCharIndex)
                    .dropLast(showMoreString.length)
                    .dropLastWhile { it == ' ' || it == '.' }

                finalText = "$adjustedText$showMoreString"
                isClickable = true
            }
        }
    }

    Text(
        text = finalText,
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onSurface,
        textAlign = TextAlign.Start,
        maxLines = if (isExpanded) Int.MAX_VALUE else 3,
        onTextLayout = { textLayoutResultState.value = it },
        modifier = modifier
            .padding(MaterialTheme.spacing.medium)
            .clickable(enabled = isClickable) { isExpanded = !isExpanded }
            .animateContentSize()
    )
}