package az.siftoshka.habitube.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.presentation.util.Padding

@Composable
fun Genre(
    genre: String
) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(Padding.Small)
    ) {
        Text(
            text = genre,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2
        )
    }
}