package az.siftoshka.habitube.presentation.screens.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import az.siftoshka.habitube.R
import az.siftoshka.habitube.domain.model.MediaLite
import az.siftoshka.habitube.domain.util.Constants.PAGE_SIZE
import az.siftoshka.habitube.domain.util.SearchType
import az.siftoshka.habitube.presentation.components.image.SearchCard
import az.siftoshka.habitube.presentation.components.screen.EmptyScreen
import az.siftoshka.habitube.presentation.components.screen.LoadingScreen
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import az.siftoshka.habitube.presentation.util.Padding
import az.siftoshka.habitube.presentation.util.Screen
import com.google.accompanist.systemuicontroller.rememberSystemUiController

/**
 * Composable function of the Search Screen.
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchViewModel = hiltViewModel()
) {
    val searchState = viewModel.searchState.value
    val page = viewModel.searchPage.value
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background)

    HabitubeV2Theme {
        Surface(color = MaterialTheme.colors.background, modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                SearchView()
                Column(modifier = Modifier.fillMaxSize()) {
                    if (searchState.isLoading) { LoadingScreen() }
                    if (searchState.media.isEmpty()) { EmptyScreen() }
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(4),
                        contentPadding = PaddingValues(Padding.Medium),
                    ) {
                        itemsIndexed(searchState.media) { index, item ->
                            viewModel.onChangeSearchPosition(index)
                            if ((index + 1) >= (page * PAGE_SIZE)) {
                                viewModel.getMoreSearchResults()
                            }
                            SearchCard(item) {
                                navigation(item, navController, viewModel.mediaType.value)
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchView(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val query = viewModel.searchQuery.value
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = FocusRequester()

    LaunchedEffect(Unit) {
        if (query.isBlank()) focusRequester.requestFocus()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(Padding.Default),
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp
    ) {
        Column {
            OutlinedTextField(
                value = query,
                singleLine = true,
                placeholder = { Text(text = stringResource(id = R.string.nav_search), style = MaterialTheme.typography.body1) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Padding.Small)
                    .focusRequester(focusRequester),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
                shape = MaterialTheme.shapes.large,
                colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = MaterialTheme.colors.primary),
                onValueChange = { viewModel.onQueryChanged(it) }
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Padding.Default),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SearchTab(SearchType.Multi) { viewModel.changeSearchType(SearchType.Multi) }
                SearchTab(SearchType.Movie) { viewModel.changeSearchType(SearchType.Movie) }
                SearchTab(SearchType.TvShow) { viewModel.changeSearchType(SearchType.TvShow) }
                SearchTab(SearchType.Person) { viewModel.changeSearchType(SearchType.Person) }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchTab(
    searchType: SearchType,
    viewModel: SearchViewModel = hiltViewModel(),
    onItemClick: () -> Unit
) {
    val mediaType = viewModel.mediaType.value
    val keyboardController = LocalSoftwareKeyboardController.current
    val backgroundColor = if (mediaType == searchType) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    val title = when (searchType) {
        SearchType.Multi -> stringResource(id = R.string.text_tab_multi)
        SearchType.Movie -> stringResource(id = R.string.text_tab_movie)
        SearchType.TvShow -> stringResource(id = R.string.text_tab_show)
        SearchType.Person -> stringResource(id = R.string.text_tab_person)
    }
    Card(
        modifier = Modifier
            .border(width = 1.dp, color = MaterialTheme.colors.primary, shape = RoundedCornerShape(100.dp)),
        shape = RoundedCornerShape(100.dp),
        backgroundColor = backgroundColor,
        onClick = {
            onItemClick()
            keyboardController?.hide()
        }
    ) {
        Text(
            text = title,
            color = MaterialTheme.colors.onSurface,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(horizontal = Padding.Medium, vertical = Padding.Small)
        )
    }
}

fun navigation(
    item: MediaLite,
    navController: NavController,
    searchType: SearchType
) {
    when (item.mediaType) {
        "movie" -> navController.navigate(Screen.MovieScreen.route + "/${item.id}")
        "tv" -> navController.navigate(Screen.TvShowScreen.route + "/${item.id}")
        "person" -> navController.navigate(Screen.PersonScreen.route + "/${item.id}")
    }
    when (searchType) {
        SearchType.Movie -> navController.navigate(Screen.MovieScreen.route + "/${item.id}")
        SearchType.TvShow -> navController.navigate(Screen.TvShowScreen.route + "/${item.id}")
        SearchType.Person -> navController.navigate(Screen.PersonScreen.route + "/${item.id}")
    }
}