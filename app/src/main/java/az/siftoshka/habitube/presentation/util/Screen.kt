package az.siftoshka.habitube.presentation.util

/**
 * The sealed class for the navigation between [Screen]-s.
 */
sealed class Screen(val route: String) {
    object ExploreScreen : Screen("explore_screen")
    object SearchScreen : Screen("search_screen")
    object LibraryScreen : Screen("library_screen")
    object SettingsScreen : Screen("settings_screen")
}