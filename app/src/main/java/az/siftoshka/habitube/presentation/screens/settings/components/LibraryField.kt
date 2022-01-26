package az.siftoshka.habitube.presentation.screens.settings.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.theme.spacing

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LibraryField(
    moviesCount: Int,
    showsCount: Int
) {
    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp,
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.extraSmall)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.default)
                    .weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.text_watched_movies),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)
                )
                Text(
                    text = moviesCount.toString(),
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onSurface,
                )
            }
            Divider(
                modifier = Modifier
                    .height(60.dp)
                    .width(1.dp), color = MaterialTheme.colors.onSurface
            )
            Column(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.default)
                    .weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.text_watched_shows),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)
                )
                Text(
                    text = showsCount.toString(),
                    style = MaterialTheme.typography.h2,
                    color = MaterialTheme.colors.onSurface,
                )
            }
        }
    }
}