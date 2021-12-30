package az.siftoshka.habitube.presentation.screens.discover.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import az.siftoshka.habitube.presentation.screens.discover.DiscoverViewModel
import az.siftoshka.habitube.presentation.screens.discover.sort.DiscoverSortItem
import az.siftoshka.habitube.presentation.util.Padding

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SortTag(
    item: DiscoverSortItem,
    viewModel: DiscoverViewModel = hiltViewModel()
) {

    val backgroundColor = if (viewModel.sortCategory.value == item.category) MaterialTheme.colors.primary else MaterialTheme.colors.background

    Card(
        modifier = Modifier
            .border(width = 1.dp, color = MaterialTheme.colors.primary, shape = RoundedCornerShape(100.dp)),
        shape = RoundedCornerShape(100.dp),
        backgroundColor = backgroundColor,
        onClick = { viewModel.sortCategory.value = item.category }
    ) {
        Text(
            text = stringResource(id = item.text),
            color = MaterialTheme.colors.onBackground,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(Padding.Small)
        )
    }
}