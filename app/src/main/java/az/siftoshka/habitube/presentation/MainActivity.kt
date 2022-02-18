package az.siftoshka.habitube.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import az.siftoshka.habitube.SharedViewModel
import az.siftoshka.habitube.domain.model.DiscoverConfiguration
import az.siftoshka.habitube.presentation.screens.discover.DiscoverScreen
import az.siftoshka.habitube.presentation.screens.discover.list.DiscoverListScreen
import az.siftoshka.habitube.presentation.screens.home.HomeScreen
import az.siftoshka.habitube.presentation.screens.library.LibraryScreen
import az.siftoshka.habitube.presentation.screens.movie.MovieScreen
import az.siftoshka.habitube.presentation.screens.person.PersonScreen
import az.siftoshka.habitube.presentation.screens.search.SearchScreen
import az.siftoshka.habitube.presentation.screens.settings.SettingsScreen
import az.siftoshka.habitube.presentation.screens.settings.content.ContentLanguageScreen
import az.siftoshka.habitube.presentation.screens.settings.language.LanguageScreen
import az.siftoshka.habitube.presentation.screens.settings.sort.SortScreen
import az.siftoshka.habitube.presentation.screens.settings.storage.StorageScreen
import az.siftoshka.habitube.presentation.screens.settings.theme.ThemeScreen
import az.siftoshka.habitube.presentation.screens.show.ShowScreen
import az.siftoshka.habitube.presentation.screens.web.WebScreen
import az.siftoshka.habitube.presentation.theme.HabitubeTheme
import az.siftoshka.habitube.presentation.theme.fontFamily
import az.siftoshka.habitube.presentation.util.Animation
import az.siftoshka.habitube.presentation.util.BottomBarScreen
import az.siftoshka.habitube.presentation.util.NavigationConstants
import az.siftoshka.habitube.presentation.util.Screen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

/**
 * The MainActivity of the app. There is only single activity.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            HabitubeTheme(sharedViewModel) {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(color = MaterialTheme.colors.background, darkIcons = sharedViewModel.isDarkIconTheme())
                val navController = rememberAnimatedNavController()
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
        BottomBarScreen.Home,
        BottomBarScreen.Discover,
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
        label = { Text(text = stringResource(id = screen.title), fontFamily = fontFamily, fontSize = 11.sp) },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = stringResource(id = screen.title),
                modifier = Modifier
                    .size(26.dp)
                    .fillMaxSize()
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(navController: NavHostController) {

    AnimatedNavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = BottomBarScreen.Discover.route) {
            DiscoverScreen(navController)
        }
        composable(
            route = Screen.DiscoverListScreen.route,
            enterTransition = { Animation.slideInHorizontally(300) },
            popExitTransition = { Animation.slideOutHorizontally(300) }
        ) {
            val discoverConfiguration =
                navController.previousBackStackEntry?.arguments?.getParcelable<DiscoverConfiguration>(NavigationConstants.PARAM_DISCOVER)
            DiscoverListScreen(navController, discoverConfiguration)
        }
        composable(route = Screen.SearchScreen.route) {
            SearchScreen(navController)
        }
        composable(route = BottomBarScreen.Library.route) {
            LibraryScreen(navController)
        }
        composable(route = BottomBarScreen.Settings.route) {
            SettingsScreen(navController)
        }
        composable(
            route = Screen.MovieScreen.route + "/{movieId}",
            enterTransition = { Animation.slideInHorizontally(300) },
            popExitTransition = { Animation.slideOutHorizontally(300) },
            exitTransition = { Animation.slideOutHorizontally(-300) },
            popEnterTransition = { Animation.slideInHorizontally(-300) }
        ) {
            MovieScreen(navController)
        }
        composable(
            route = Screen.TvShowScreen.route + "/{showId}",
            enterTransition = { Animation.slideInHorizontally(300) },
            popExitTransition = { Animation.slideOutHorizontally(300) },
            exitTransition = { Animation.slideOutHorizontally(-300) },
            popEnterTransition = { Animation.slideInHorizontally(-300) }
        ) {
            ShowScreen(navController)
        }
        composable(
            route = Screen.PersonScreen.route + "/{personId}",
            enterTransition = { Animation.slideInHorizontally(300) },
            popExitTransition = { Animation.slideOutHorizontally(300) },
            exitTransition = { Animation.slideOutHorizontally(-300) },
            popEnterTransition = { Animation.slideInHorizontally(-300) }
        ) {
            PersonScreen(navController)
        }
        composable(
            route = Screen.ThemeScreen.route,
            enterTransition = { Animation.slideInHorizontally(300) },
            popExitTransition = { Animation.slideOutHorizontally(300) }
        ) {
            ThemeScreen(navController)
        }
        composable(
            route = Screen.LanguageScreen.route,
            enterTransition = { Animation.slideInHorizontally(300) },
            popExitTransition = { Animation.slideOutHorizontally(300) }
        ) {
            LanguageScreen(navController)
        }
        composable(
            route = Screen.ContentLanguageScreen.route,
            enterTransition = { Animation.slideInHorizontally(300) },
            popExitTransition = { Animation.slideOutHorizontally(300) }
        ) {
            ContentLanguageScreen(navController)
        }
        composable(
            route = Screen.StorageScreen.route,
            enterTransition = { Animation.slideInHorizontally(300) },
            popExitTransition = { Animation.slideOutHorizontally(300) }
        ) {
            StorageScreen(navController)
        }
        composable(
            route = Screen.SortScreen.route,
            enterTransition = { Animation.slideInHorizontally(300) },
            popExitTransition = { Animation.slideOutHorizontally(300) }
        ) {
            SortScreen(navController)
        }
        composable(
            route = Screen.WebScreen.route + "/{url}",
            enterTransition = { Animation.slideInHorizontally(300) },
            popExitTransition = { Animation.slideOutHorizontally(300) }
        ) { navBackStackEntry ->
            navBackStackEntry.arguments?.getString("url")?.let {
                WebScreen(value = it, navController = navController)
            }
        }
    }
}