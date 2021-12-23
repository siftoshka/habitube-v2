package az.siftoshka.habitube.presentation.screens.library

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.presentation.RealtimeViewModel
import az.siftoshka.habitube.presentation.components.image.LibraryCard
import az.siftoshka.habitube.presentation.components.screen.LibraryEmptyScreen
import az.siftoshka.habitube.presentation.screens.library.boards.MovieBoard
import az.siftoshka.habitube.presentation.screens.library.boards.ShowBoard
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import az.siftoshka.habitube.presentation.util.Padding
import com.google.accompanist.pager.*
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.launch

/**
 * Composable function of the Library Screen.
 */
@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun LibraryScreen(
    navController: NavController,
    viewModel: LibraryViewModel = hiltViewModel(),
    realtimeViewModel: RealtimeViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    realtimeViewModel.syncData()

    HabitubeV2Theme {
        ModalBottomSheetLayout(
            sheetState = sheetState,
            sheetBackgroundColor = MaterialTheme.colors.surface,
            sheetShape = MaterialTheme.shapes.large,
            scrimColor = Color.Transparent,
            sheetContent = {
                if (viewModel.isMoviesSelected.value) {
                    MovieBoard(isWatched = viewModel.isWatched.value, navController, sheetState)
                } else {
                    ShowBoard(isWatched = viewModel.isWatched.value, navController, sheetState)
                }
            }
        ) {
            BackHandler(
                enabled = (sheetState.currentValue == ModalBottomSheetValue.Expanded),
                onBack = { scope.launch { sheetState.hide() } }
            )
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
                    TabView(pagerState, sheetState) {
                        scope.launch { pagerState.animateScrollToPage(it) }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalMaterialApi::class)
@Composable
fun TabView(
    pagerState: PagerState,
    sheetState: ModalBottomSheetState,
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
            0 -> WatchedTab(sheetState)
            1 -> PlanningTab(sheetState)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun WatchedTab(
    sheetState: ModalBottomSheetState,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {
        if (viewModel.watchedMovies.value.isEmpty() && viewModel.isMoviesSelected.value) LibraryEmptyScreen()
        if (viewModel.watchedShows.value.isEmpty() && !viewModel.isMoviesSelected.value) LibraryEmptyScreen()
        LazyVerticalGrid(
            cells = GridCells.Fixed(4),
            contentPadding = PaddingValues(Padding.Medium),
        ) {
            if (viewModel.isMoviesSelected.value) {
                itemsIndexed(viewModel.watchedMovies.value) { index, item ->
                    LibraryCard(context, title = item.title.orEmpty(), imageUrl = item.posterPath.orEmpty(), rating = item.myRating, true) {
                        viewModel.isWatched.value = true
                        viewModel.mediaId.value = item.id ?: 0
                        scope.launch { sheetState.show() }
                    }
                }
            } else if (viewModel.isShowsSelected.value) {
                itemsIndexed(viewModel.watchedShows.value) { index, item ->
                    LibraryCard(context, title = item.name.orEmpty(), imageUrl = item.posterPath.orEmpty(), rating = item.myRating, true) {
                        viewModel.isWatched.value = true
                        viewModel.mediaId.value = item.id ?: 0
                        scope.launch { sheetState.show() }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun PlanningTab(
    sheetState: ModalBottomSheetState,
    viewModel: LibraryViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {
        if (viewModel.plannedMovies.value.isEmpty() && viewModel.isMoviesSelected.value) LibraryEmptyScreen()
        if (viewModel.plannedShows.value.isEmpty() && !viewModel.isMoviesSelected.value) LibraryEmptyScreen()
        LazyVerticalGrid(
            cells = GridCells.Fixed(4),
            contentPadding = PaddingValues(Padding.Medium),
        ) {
            if (viewModel.isMoviesSelected.value) {
                itemsIndexed(viewModel.plannedMovies.value) { index, item ->
                    LibraryCard(context, title = item.title.orEmpty(), imageUrl = item.posterPath.orEmpty(), isWatched = false) {
                        viewModel.isWatched.value = false
                        viewModel.mediaId.value = item.id ?: 0
                        scope.launch { sheetState.show() }
                    }
                }
            } else if (viewModel.isShowsSelected.value) {
                itemsIndexed(viewModel.plannedShows.value) { index, item ->
                    LibraryCard(context, title = item.name.orEmpty(), imageUrl = item.posterPath.orEmpty(), isWatched = false) {
                        viewModel.isWatched.value = false
                        viewModel.mediaId.value = item.id ?: 0
                        scope.launch { sheetState.show() }
                    }
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