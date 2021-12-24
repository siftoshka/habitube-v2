package az.siftoshka.habitube.presentation.screens.settings.fileds

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.util.Padding

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
        modifier = Modifier.padding(vertical = Padding.ExtraSmall)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .padding(Padding.Default)
                    .weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${stringResource(id = R.string.text_watched)} ${stringResource(id = R.string.text_movies)}",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(bottom = Padding.Small)
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
                    .padding(Padding.Default)
                    .weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${stringResource(id = R.string.text_watched)} ${stringResource(id = R.string.text_shows)}",
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface,
                    modifier = Modifier.padding(bottom = Padding.Small)
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