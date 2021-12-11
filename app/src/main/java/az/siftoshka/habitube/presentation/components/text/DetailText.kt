package az.siftoshka.habitube.presentation.components.text

import androidx.annotation.StringRes
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun DetailText(
    @StringRes name: Int,
    detail: String
) {
    Text(
        text = buildAnnotatedString {
            append(stringResource(id = name))
            append(AnnotatedString(detail, spanStyle = SpanStyle(color = MaterialTheme.colors.secondaryVariant, fontWeight = FontWeight.Light)))
        },
        style = MaterialTheme.typography.body1,
        color = MaterialTheme.colors.onSurface,
        textAlign = TextAlign.Start
    )
}