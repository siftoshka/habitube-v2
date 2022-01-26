package az.siftoshka.habitube.presentation.util

/**
 * The sealed class for the navigation between [Screen]-s.
 */
sealed class Screen(val route: String) {
    object HomeScreen : Screen("nav_home_screen")
    object DiscoverScreen : Screen("nav_discover_screen")
    object LibraryScreen : Screen("nav_library_screen")
    object SettingsScreen : Screen("nav_settings_screen")
    object SearchScreen : Screen("search_screen")
    object DiscoverListScreen : Screen("discover_list_screen")
    object MovieScreen : Screen("movie_screen")
    object TvShowScreen : Screen("tv_show_screen")
    object PersonScreen : Screen("person_screen")
    object LanguageScreen : Screen("language_screen")
    object ThemeScreen : Screen("theme_screen")
    object ContentLanguageScreen : Screen("content_language_screen")
    object StorageScreen : Screen("storage_screen")
    object SortScreen : Screen("sort_screen")
    object WebScreen : Screen("web_screen")
}

object NavigationConstants {

    const val PARAM_MOVIE_ID = "movieId"
    const val PARAM_TV_SHOW_ID = "showId"
    const val PARAM_PERSON_ID = "personId"
    const val PARAM_DISCOVER = "discoverConfig"
}