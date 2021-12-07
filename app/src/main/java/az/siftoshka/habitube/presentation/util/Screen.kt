package az.siftoshka.habitube.presentation.util

/**
 * The sealed class for the navigation between [Screen]-s.
 */
sealed class Screen(val route: String) {
    object ExploreScreen : Screen("nav_explore_screen")
    object SearchScreen : Screen("nav_search_screen")
    object LibraryScreen : Screen("nav_library_screen")
    object SettingsScreen : Screen("nav_settings_screen")
    object MovieScreen : Screen("movie_screen")
    object TvShowScreen : Screen("tv_show_screen")
}

object NavigationConstants {

    const val PARAM_MOVIE_ID = "movieId"
    const val PARAM_TV_SHOW_ID = "showId"
}