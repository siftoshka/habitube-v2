package az.siftoshka.habitube.presentation.screens.library.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.presentation.theme.spacing

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LibraryTextCard(
    title: String,
    rating: Float? = 0f,
    onPerformClick: () -> Unit
) {
    val myRating = if (rating?.let { it.compareTo(0f) > 0 } == true) rating else ""

    Card(
        modifier = Modifier.fillMaxWidth(),
        backgroundColor = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.large,
        elevation = 4.dp,
        onClick = { onPerformClick() }
    ) {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = title,
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(MaterialTheme.spacing.medium).weight(0.8f),
                maxLines = 1
            )
            Text(
                text = myRating.toString(),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface,
                textAlign = TextAlign.End,
                modifier = Modifier.padding(MaterialTheme.spacing.medium).weight(0.2f)
            )
        }
    }
}