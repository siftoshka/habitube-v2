package az.siftoshka.habitube.presentation.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import az.siftoshka.habitube.R

/**
 * The sealed class for the bottom navigation [Screen]-s.
 */
sealed class BottomBarScreen(
    val route: String,
    @StringRes val title: Int,
    @DrawableRes val icon: Int
) {
    object Home: BottomBarScreen(
        route = Screen.HomeScreen.route,
        title = R.string.nav_home,
        icon = R.drawable.ic_home
    )
    object Discover: BottomBarScreen(
        route = Screen.DiscoverScreen.route,
        title = R.string.nav_discover,
        icon = R.drawable.ic_discover
    )
    object Library: BottomBarScreen(
        route = Screen.LibraryScreen.route,
        title = R.string.nav_library,
        icon = R.drawable.ic_library
    )
    object Settings: BottomBarScreen(
        route = Screen.SettingsScreen.route,
        title = R.string.nav_settings,
        icon = R.drawable.ic_settings
    )
}