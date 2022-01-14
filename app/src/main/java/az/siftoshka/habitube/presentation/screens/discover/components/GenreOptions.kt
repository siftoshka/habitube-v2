package az.siftoshka.habitube.presentation.screens.discover.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import az.siftoshka.habitube.presentation.screens.discover.DiscoverViewModel
import az.siftoshka.habitube.presentation.screens.discover.items.genres
import az.siftoshka.habitube.presentation.screens.discover.items.showGenres
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun GenreOptions(
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    FlowRow(
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = (-8).dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (viewModel.isMovieSelected.value) {
            genres.forEach { item -> GenreTag(item) }
        } else {
            showGenres.forEach { item -> GenreTag(item) }
        }
    }
}