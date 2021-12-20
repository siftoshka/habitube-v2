package az.siftoshka.habitube.presentation.screens.library

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import az.siftoshka.habitube.presentation.util.Padding
import com.google.accompanist.pager.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

/**
 * Composable function of the Library screen.
 */
@OptIn(ExperimentalPagerApi::class)
@Composable
fun LibraryScreen(
    navController: NavController,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    HabitubeV2Theme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .padding(horizontal = Padding.Default)
                        .padding(top = Padding.Default)
                ) {
                    LibraryTitle(title = stringResource(id = R.string.text_movies), isSelected = viewModel.isMoviesSelected.value) { isSelected ->
                        viewModel.isMoviesSelected.value = isSelected
                        viewModel.isShowsSelected.value = !isSelected
                        viewModel.updateConfiguration()
                    }
                    LibraryTitle(title = stringResource(id = R.string.text_shows), isSelected = viewModel.isShowsSelected.value) { isSelected ->
                        viewModel.isShowsSelected.value = isSelected
                        viewModel.isMoviesSelected.value = !isSelected
                        viewModel.updateConfiguration()
                    }
                }
                TabView(pagerState) {
                    scope.launch { pagerState.animateScrollToPage(it) }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabView(
    pagerState: PagerState,
    onTabSelected: (selectedIndex: Int) -> Unit
) {
    val tabs = listOf(R.string.text_watched, R.string.text_planning)
    val inactiveColor = MaterialTheme.colors.onBackground

    TabRow(
        indicator = { tabPositions -> TabRowDefaults.Indicator(Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)) },
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.primary,
        selectedTabIndex = pagerState.currentPage
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = inactiveColor,
                onClick = { onTabSelected(index) },
                text = {
                    Text(
                        text = stringResource(id = title),
                        style = MaterialTheme.typography.h4,
                        color = if (pagerState.currentPage == index) MaterialTheme.colors.primary else inactiveColor
                    )
                }
            )
        }
    }
    HorizontalPager(count = tabs.size, state = pagerState) { page ->
        when (page) {
            0 -> WatchedTab()
            1 -> PlanningTab()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WatchedTab(
    viewModel: LibraryViewModel = hiltViewModel()
) {
    Column(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(4),
            contentPadding = PaddingValues(Padding.Medium),
        ) {
            if (viewModel.isMoviesSelected.value) {
                itemsIndexed(viewModel.watchedMovies.value) { index, item ->
                    println("WATCHED MOVIES" + item.id)
                }
            } else if (viewModel.isShowsSelected.value) {
                itemsIndexed(viewModel.watchedShows.value) { index, item ->
                    println("WATCHED SHOWS" + item.id)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlanningTab(
    viewModel: LibraryViewModel = hiltViewModel()
) {
    Column(Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(4),
            contentPadding = PaddingValues(Padding.Medium),
        ) {
            if (viewModel.isMoviesSelected.value) {
                itemsIndexed(viewModel.plannedMovies.value) { index, item ->
                    println("PLANNED MOVIES" + item.id)
                }
            } else if (viewModel.isShowsSelected.value) {
                itemsIndexed(viewModel.plannedShows.value) { index, item ->
                    println("PLANNED SHOWS" + item.id)
                }
            }
        }
    }
}

@Composable
fun LibraryTitle(
    title: String,
    isSelected: Boolean,
    onPerformClick: (value: Boolean) -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colors.onBackground else MaterialTheme.colors.secondaryVariant

    Text(
        text = title,
        style = MaterialTheme.typography.h1,
        color = backgroundColor,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .padding(end = Padding.Small)
            .indication(indication = null, interactionSource = MutableInteractionSource())
            .clickable { onPerformClick(!isSelected) }
    )
}