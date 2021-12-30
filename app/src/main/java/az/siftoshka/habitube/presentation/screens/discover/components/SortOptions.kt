package az.siftoshka.habitube.presentation.screens.discover.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import az.siftoshka.habitube.presentation.screens.discover.DiscoverViewModel
import az.siftoshka.habitube.presentation.screens.discover.sort.showTypes
import az.siftoshka.habitube.presentation.screens.discover.sort.types
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun SortOptions(
    viewModel: DiscoverViewModel = hiltViewModel()
) {
    FlowRow(
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 10.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (viewModel.isMovieSelected.value) {
            types.forEach { item -> SortTag(item) }
        } else {
            showTypes.forEach { item -> SortTag(item) }
        }
    }
}