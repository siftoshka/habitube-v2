package az.siftoshka.habitube.presentation.screens.home.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import az.siftoshka.habitube.presentation.theme.spacing

@Composable
fun TitleText(
    @StringRes text: Int,
    @StringRes secondary: Int? = null
) {
    Text(
        text = buildAnnotatedString {
            append(stringResource(id = text))
            if (secondary != null) {
                append(
                    AnnotatedString(
                        " ${stringResource(id = secondary)}",
                        spanStyle = SpanStyle(fontSize = 16.sp, color = MaterialTheme.colors.secondaryVariant)
                    )
                )
            }
        },
        style = MaterialTheme.typography.h2,
        color = MaterialTheme.colors.onBackground,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default, vertical = MaterialTheme.spacing.extraSmall)
    )
}