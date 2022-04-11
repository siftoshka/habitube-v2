package az.siftoshka.habitube.presentation.screens.library

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.util.isInternetAvailable
import az.siftoshka.habitube.presentation.components.screen.LoadingScreen
import az.siftoshka.habitube.presentation.screens.library.boards.MovieBoard
import az.siftoshka.habitube.presentation.screens.library.boards.ShowBoard
import az.siftoshka.habitube.presentation.screens.library.components.LibraryCard
import az.siftoshka.habitube.presentation.screens.library.components.LibraryEmptyScreen
import az.siftoshka.habitube.presentation.screens.library.components.LibraryTextCard
import az.siftoshka.habitube.presentation.theme.spacing
import com.google.accompanist.pager.*
import kotlinx.coroutines.Dispatchers
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
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(true) {
        scope.launch(Dispatchers.Default) {
            realtimeViewModel.syncData()
            viewModel.updateConfiguration()
        }
    }

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
                        .padding(horizontal = MaterialTheme.spacing.default)
                        .padding(top = MaterialTheme.spacing.default)
                ) {
                    LibraryTitle(title = stringResource(id = R.string.text_movies), isSelected = viewModel.isMoviesSelected.value) { isSelected ->
                        if (!viewModel.isMoviesSelected.value) {
                            viewModel.isMoviesSelected.value = isSelected
                            viewModel.isShowsSelected.value = !isSelected
                            viewModel.updateConfiguration()
                        }
                    }
                    LibraryTitle(title = stringResource(id = R.string.text_shows), isSelected = viewModel.isShowsSelected.value) { isSelected ->
                        if (!viewModel.isShowsSelected.value) {
                            viewModel.isShowsSelected.value = isSelected
                            viewModel.isMoviesSelected.value = !isSelected
                            viewModel.updateConfiguration()
                        }
                    }
                }
                TabView(pagerState, sheetState) {
                    scope.launch { pagerState.animateScrollToPage(it) }
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
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(Modifier.fillMaxSize()) {
        if (viewModel.isLoading.value) LoadingScreen()
        if (viewModel.watchedMovies.value.isEmpty() && viewModel.isMoviesSelected.value) LibraryEmptyScreen()
        if (viewModel.watchedShows.value.isEmpty() && !viewModel.isMoviesSelected.value) LibraryEmptyScreen()
        if (context.isInternetAvailable()) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(4),
                contentPadding = PaddingValues(MaterialTheme.spacing.medium),
            ) {
                if (viewModel.isMoviesSelected.value) {
                    itemsIndexed(viewModel.watchedMovies.value) { index, item ->
                        LibraryCard(title = item.title.orEmpty(), imageUrl = item.posterPath.orEmpty(), rating = item.myRating) {
                            viewModel.isWatched.value = true
                            viewModel.mediaId.value = item.id ?: 0
                            scope.launch { sheetState.show() }
                        }
                    }
                } else if (viewModel.isShowsSelected.value) {
                    itemsIndexed(viewModel.watchedShows.value) { index, item ->
                        LibraryCard(title = item.name.orEmpty(), imageUrl = item.posterPath.orEmpty(), rating = item.myRating) {
                            viewModel.isWatched.value = true
                            viewModel.mediaId.value = item.id ?: 0
                            scope.launch { sheetState.show() }
                        }
                    }
                }
            }
        } else {
            LazyColumn(contentPadding = PaddingValues(MaterialTheme.spacing.medium)) {
                if (viewModel.isMoviesSelected.value) {
                    itemsIndexed(viewModel.watchedMovies.value) { index, item ->
                        LibraryTextCard(title = item.title.orEmpty(), rating = item.myRating) {
                            viewModel.isWatched.value = true
                            viewModel.mediaId.value = item.id ?: 0
                            scope.launch { sheetState.show() }
                        }
                    }
                } else if (viewModel.isShowsSelected.value) {
                    itemsIndexed(viewModel.watchedShows.value) { index, item ->
                        LibraryTextCard(title = item.name.orEmpty(), rating = item.myRating) {
                            viewModel.isWatched.value = true
                            viewModel.mediaId.value = item.id ?: 0
                            scope.launch { sheetState.show() }
                        }
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
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(Modifier.fillMaxSize()) {
        if (viewModel.plannedMovies.value.isEmpty() && viewModel.isMoviesSelected.value) LibraryEmptyScreen()
        if (viewModel.plannedShows.value.isEmpty() && !viewModel.isMoviesSelected.value) LibraryEmptyScreen()
        if (context.isInternetAvailable()) {
            LazyVerticalGrid(
                cells = GridCells.Fixed(4),
                contentPadding = PaddingValues(MaterialTheme.spacing.medium),
            ) {
                if (viewModel.isMoviesSelected.value) {
                    itemsIndexed(viewModel.plannedMovies.value) { index, item ->
                        LibraryCard(title = item.title.orEmpty(), imageUrl = item.posterPath.orEmpty()) {
                            viewModel.isWatched.value = false
                            viewModel.mediaId.value = item.id ?: 0
                            scope.launch { sheetState.show() }
                        }
                    }
                } else if (viewModel.isShowsSelected.value) {
                    itemsIndexed(viewModel.plannedShows.value) { index, item ->
                        LibraryCard(title = item.name.orEmpty(), imageUrl = item.posterPath.orEmpty()) {
                            viewModel.isWatched.value = false
                            viewModel.mediaId.value = item.id ?: 0
                            scope.launch { sheetState.show() }
                        }
                    }
                }
            }
        } else {
            LazyColumn(contentPadding = PaddingValues(MaterialTheme.spacing.medium)) {
                if (viewModel.isMoviesSelected.value) {
                    itemsIndexed(viewModel.plannedMovies.value) { index, item ->
                        LibraryTextCard(title = item.title.orEmpty()) {
                            viewModel.isWatched.value = false
                            viewModel.mediaId.value = item.id ?: 0
                            scope.launch { sheetState.show() }
                        }
                    }
                } else if (viewModel.isShowsSelected.value) {
                    itemsIndexed(viewModel.plannedShows.value) { index, item ->
                        LibraryTextCard(title = item.name.orEmpty()) {
                            viewModel.isWatched.value = false
                            viewModel.mediaId.value = item.id ?: 0
                            scope.launch { sheetState.show() }
                        }
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
            .padding(end = MaterialTheme.spacing.small)
            .clickable(interactionSource = MutableInteractionSource(), indication = null) { onPerformClick(!isSelected) }
    )
}