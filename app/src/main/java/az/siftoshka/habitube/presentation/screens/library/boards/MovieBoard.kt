package az.siftoshka.habitube.presentation.screens.library.boards

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.deleteFromStorage
import az.siftoshka.habitube.domain.util.renameFileToWatched
import az.siftoshka.habitube.presentation.screens.library.LibraryViewModel
import az.siftoshka.habitube.presentation.screens.library.sections.InfoMovieSection
import az.siftoshka.habitube.presentation.util.Padding
import az.siftoshka.habitube.presentation.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MovieBoard(
    isWatched: Boolean,
    navController: NavController,
    sheetState: ModalBottomSheetState,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    if (isWatched) {
        viewModel.getWatchedMovie()
        InfoMovieSection(movie = viewModel.movieState.value, isWatched)
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Padding.Default)
                .padding(bottom = Padding.ExtraLarge)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = MaterialTheme.colors.primary, shape = MaterialTheme.shapes.large),
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.surface,
                indication = rememberRipple(color = MaterialTheme.colors.primary),
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        navController.navigate(Screen.MovieScreen.route + "/${viewModel.movieState.value.id}")
                    }
                }
            ) {
                Text(
                    text = stringResource(id = R.string.text_full_info),
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(Padding.Medium)
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Padding.Medium)
                    .border(width = 1.dp, color = MaterialTheme.colors.error, shape = MaterialTheme.shapes.large),
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.surface,
                indication = rememberRipple(color = MaterialTheme.colors.error),
                onClick = {
                    scope.launch { sheetState.hide() }
                    viewModel.deleteMovie(viewModel.movieState.value, isWatched)
                    context.deleteFromStorage(viewModel.movieState.value.posterPath, true)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.text_remove_watched),
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(Padding.Medium)
                )
            }
        }
    } else {
        viewModel.getPlannedMovie()
        InfoMovieSection(movie = viewModel.movieState.value, isWatched)
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Padding.Default)
                .padding(bottom = Padding.ExtraLarge)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(width = 1.dp, color = MaterialTheme.colors.primary, shape = MaterialTheme.shapes.large),
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.surface,
                indication = rememberRipple(color = MaterialTheme.colors.primary),
                onClick = {
                    scope.launch {
                        sheetState.hide()
                        navController.navigate(Screen.MovieScreen.route + "/${viewModel.movieState.value.id}")
                    }
                }
            ) {
                Text(
                    text = stringResource(id = R.string.text_full_info),
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(Padding.Medium)
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Padding.Medium)
                    .border(width = 1.dp, color = MaterialTheme.colors.secondaryVariant, shape = MaterialTheme.shapes.large),
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.surface,
                indication = rememberRipple(color = MaterialTheme.colors.secondaryVariant),
                onClick = {
                    scope.launch { sheetState.hide() }
                    viewModel.switchToWatchedMovie(viewModel.movieState.value)
                    context.renameFileToWatched(viewModel.movieState.value.posterPath)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.text_already_watched),
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(Padding.Medium)
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Padding.Medium)
                    .border(width = 1.dp, color = MaterialTheme.colors.error, shape = MaterialTheme.shapes.large),
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.surface,
                indication = rememberRipple(color = MaterialTheme.colors.error),
                onClick = {
                    scope.launch { sheetState.hide() }
                    viewModel.deleteMovie(viewModel.movieState.value, isWatched)
                    context.deleteFromStorage(viewModel.movieState.value.posterPath, false)
                }
            ) {
                Text(
                    text = stringResource(id = R.string.text_remove_planning),
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(Padding.Medium)
                )
            }
        }
    }
}