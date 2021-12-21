package az.siftoshka.habitube.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import az.siftoshka.habitube.domain.util.isInternetAvailable
import az.siftoshka.habitube.presentation.screens.explore.ExploreScreen
import az.siftoshka.habitube.presentation.screens.library.LibraryScreen
import az.siftoshka.habitube.presentation.screens.movie.MovieScreen
import az.siftoshka.habitube.presentation.screens.person.PersonScreen
import az.siftoshka.habitube.presentation.screens.search.SearchScreen
import az.siftoshka.habitube.presentation.screens.settings.SettingsScreen
import az.siftoshka.habitube.presentation.screens.settings.language.LanguageScreen
import az.siftoshka.habitube.presentation.screens.show.ShowScreen
import az.siftoshka.habitube.presentation.theme.HabitubeV2Theme
import az.siftoshka.habitube.presentation.theme.fontFamily
import az.siftoshka.habitube.presentation.util.BottomBarScreen
import az.siftoshka.habitube.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

/**
 * The MainActivity of the app. There is only single activity.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            HabitubeV2Theme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route?.substringBeforeLast("/")

                Scaffold(
                    bottomBar = {
                        if (currentRoute?.contains("nav") == true) {
                            BottomBar(navController = navController)
                        }
                    }
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavGraph(navController = navController)
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Explore,
        BottomBarScreen.Search,
        BottomBarScreen.Library,
        BottomBarScreen.Settings
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        elevation = 4.dp
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = { Text(text = stringResource(id = screen.title), fontFamily = fontFamily) },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = stringResource(id = screen.title),
                modifier = Modifier.size(26.dp)
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        selectedContentColor = MaterialTheme.colors.primary,
        unselectedContentColor = MaterialTheme.colors.secondaryVariant,
        onClick = {
            if (currentDestination?.route != screen.route) {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        }
    )
}

@Composable
fun NavGraph(navController: NavHostController) {

    val context = LocalContext.current
    val startDestination = if (context.isInternetAvailable()) BottomBarScreen.Explore.route else BottomBarScreen.Library.route

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = BottomBarScreen.Explore.route) {
            ExploreScreen(navController)
        }
        composable(route = BottomBarScreen.Search.route) {
            SearchScreen(navController)
        }
        composable(route = BottomBarScreen.Library.route) {
            LibraryScreen(navController)
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen(navController)
        }
        composable(route = Screen.MovieScreen.route + "/{movieId}") {
            MovieScreen(navController)
        }
        composable(route = Screen.TvShowScreen.route + "/{showId}") {
            ShowScreen(navController)
        }
        composable(route = Screen.PersonScreen.route + "/{personId}") {
            PersonScreen(navController)
        }
        composable(route = Screen.LanguageScreen.route) {
            LanguageScreen(navController)
        }
    }
}