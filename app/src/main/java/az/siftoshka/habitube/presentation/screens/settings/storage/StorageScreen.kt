package az.siftoshka.habitube.presentation.screens.settings.storage

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.components.TopAppBar
import az.siftoshka.habitube.presentation.theme.spacing

/**
 * Composable function of Storage Screen.
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun StorageScreen(
    navController: NavController,
    viewModel: StorageViewModel = hiltViewModel()
) {
    val dialogState = remember { mutableStateOf(false) }
    val storageType = remember { mutableStateOf(StorageType.WATCHED_MOVIES) }

    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = R.string.text_storage,
                icon = R.drawable.ic_back,
            ) { navController.popBackStack() }
            Spacer(modifier = Modifier.height(16.dp))
            Column(Modifier.padding(horizontal = MaterialTheme.spacing.default)) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(list.size) {
                        val item = list[it]
                        StorageRowItem(item) { type ->
                            storageType.value = type
                            dialogState.value = true
                        }
                    }
                }
                StorageDialog(
                    title = R.string.text_storage_dialog_title,
                    text = R.string.text_storage_dialog_description,
                    positiveButton = R.string.text_delete,
                    state = dialogState,
                    onPerformClick = {
                        when (storageType.value) {
                            StorageType.WATCHED_MOVIES -> viewModel.deleteWatchedMovies()
                            StorageType.WATCHED_SHOWS -> viewModel.deleteWatchedTvShows()
                            StorageType.PLANNED_MOVIES -> viewModel.deletePlannedMovies()
                            StorageType.PLANNED_SHOWS -> viewModel.deletePlannedTvShows()
                            StorageType.ALL -> viewModel.deleteAll()
                        }
                        dialogState.value = false
                    },
                    onCancel = { dialogState.value = false }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun StorageRowItem(
    item: StorageItem,
    onPerformClick: (StorageType) -> Unit
) {
    if (item.type == StorageType.ALL) {
        Spacer(Modifier.height(24.dp))
    }
    Card(
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        onClick = { onPerformClick(item.type) },
        elevation = 4.dp,
        modifier = Modifier.padding(vertical = MaterialTheme.spacing.extraSmall)
    ) {
        ListItem(
            text = {
                Text(
                    text = stringResource(id = item.text),
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.onSurface,
                )
            }
        )
    }
}