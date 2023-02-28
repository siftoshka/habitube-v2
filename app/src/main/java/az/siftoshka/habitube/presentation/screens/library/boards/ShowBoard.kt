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
import az.siftoshka.habitube.domain.util.isInternetAvailable
import az.siftoshka.habitube.presentation.screens.library.LibraryViewModel
import az.siftoshka.habitube.presentation.screens.library.sections.InfoShowSection
import az.siftoshka.habitube.presentation.theme.spacing
import az.siftoshka.habitube.presentation.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowBoard(
    isWatched: Boolean,
    navController: NavController,
    sheetState: ModalBottomSheetState,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    if (isWatched) {
        viewModel.getWatchedTvShow()
        InfoShowSection(show = viewModel.showState.value, isWatched)
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.default)
                .padding(bottom = MaterialTheme.spacing.extraLarge)
        ) {
            if (context.isInternetAvailable()) {
                rememberRipple(color = MaterialTheme.colors.primary)
                Card(
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            navController.navigate(Screen.TvShowScreen.route + "/${viewModel.showState.value.id}")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Text(
                        text = stringResource(id = R.string.text_full_info),
                        color = MaterialTheme.colors.onSurface,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(MaterialTheme.spacing.medium)
                    )
                }
            }
            rememberRipple(color = MaterialTheme.colors.error)
            Card(
                onClick = {
                    scope.launch { sheetState.hide() }
                    viewModel.deleteShow(viewModel.showState.value, isWatched)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.medium)
                    .border(width = 1.dp, color = MaterialTheme.colors.error, shape = MaterialTheme.shapes.large),
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.surface
            ) {
                Text(
                    text = stringResource(id = R.string.text_remove_watched),
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(MaterialTheme.spacing.medium)
                )
            }
        }
    } else {
        viewModel.getPlannedTVShow()
        InfoShowSection(show = viewModel.showState.value, isWatched)
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.default)
                .padding(bottom = MaterialTheme.spacing.extraLarge)
        ) {
            if (context.isInternetAvailable()) {
                rememberRipple(color = MaterialTheme.colors.primary)
                Card(
                    onClick = {
                        scope.launch {
                            sheetState.hide()
                            navController.navigate(Screen.TvShowScreen.route + "/${viewModel.showState.value.id}")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.large,
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Text(
                        text = stringResource(id = R.string.text_full_info),
                        color = MaterialTheme.colors.onSurface,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h4,
                        modifier = Modifier.padding(MaterialTheme.spacing.medium)
                    )
                }
            }
            rememberRipple(color = MaterialTheme.colors.secondaryVariant)
            Card(
                onClick = {
                    scope.launch { sheetState.hide() }
                    viewModel.switchToWatchedShow(viewModel.showState.value)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.medium)
                    .border(width = 1.dp, color = MaterialTheme.colors.secondaryVariant, shape = MaterialTheme.shapes.large),
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.surface
            ) {
                Text(
                    text = stringResource(id = R.string.text_already_watched),
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(MaterialTheme.spacing.medium)
                )
            }
            rememberRipple(color = MaterialTheme.colors.error)
            Card(
                onClick = {
                    scope.launch { sheetState.hide() }
                    viewModel.deleteShow(viewModel.showState.value, isWatched)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.medium)
                    .border(width = 1.dp, color = MaterialTheme.colors.error, shape = MaterialTheme.shapes.large),
                shape = MaterialTheme.shapes.large,
                backgroundColor = MaterialTheme.colors.surface
            ) {
                Text(
                    text = stringResource(id = R.string.text_remove_planning),
                    color = MaterialTheme.colors.onSurface,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(MaterialTheme.spacing.medium)
                )
            }
        }
    }
}