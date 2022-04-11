package az.siftoshka.habitube.presentation.screens.settings.sort

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.components.TopAppBar
import az.siftoshka.habitube.presentation.theme.spacing

/**
 * Composable function of Sort Screen.
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun SortScreen(
    navController: NavController,
    viewModel: SortViewModel = hiltViewModel()
) {
    Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = R.string.text_sort,
                icon = R.drawable.ic_back,
            ) { navController.popBackStack() }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.default)
                    .fillMaxWidth()
            ) {
                items(list.size) {
                    val item = list[it]
                    SortRowItem(item) { type ->
                        viewModel.setSortType(type.name)
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SortRowItem(
    item: SortItem,
    viewModel: SortViewModel = hiltViewModel(),
    onPerformClick: (SortType) -> Unit
) {
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
            },
            trailing = {
                if (viewModel.getSortType() == item.type) {
                    OutlinedButton(
                        onClick = { onPerformClick(item.type) },
                        shape = RoundedCornerShape(20.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colors.primary),
                        colors = ButtonDefaults.outlinedButtonColors(
                            backgroundColor = Color.Transparent,
                            contentColor = MaterialTheme.colors.primary
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.text_selected),
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
        )
    }
}