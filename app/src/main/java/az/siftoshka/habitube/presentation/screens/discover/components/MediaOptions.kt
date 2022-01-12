package az.siftoshka.habitube.presentation.screens.discover.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.screens.discover.DiscoverViewModel
import az.siftoshka.habitube.presentation.theme.spacing

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MediaOptions(viewModel: DiscoverViewModel = hiltViewModel()) {

    val movieTextColor = if (viewModel.isMovieSelected.value) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
    val showTextColor = if (viewModel.isMovieSelected.value) MaterialTheme.colors.onSurface else MaterialTheme.colors.primary

    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp,
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.extraSmall)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = Modifier
                    .clickable(indication = null, interactionSource = MutableInteractionSource()) {
                        viewModel.isMovieSelected.value = true
                        viewModel.clear()
                    }
                    .weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.text_movies),
                    style = MaterialTheme.typography.h4,
                    color = movieTextColor,
                    textAlign = TextAlign.Center,
                )
            }
            Divider(
                modifier = Modifier
                    .height(40.dp)
                    .width(1.dp), color = MaterialTheme.colors.onSurface
            )
            Column(
                modifier = Modifier
                    .clickable(indication = null, interactionSource = MutableInteractionSource()) {
                        viewModel.isMovieSelected.value = false
                        viewModel.clear()
                    }
                    .weight(1f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.text_shows),
                    style = MaterialTheme.typography.h4,
                    color = showTextColor,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}