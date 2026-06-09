package com.example.moovie.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.example.moovie.R
import com.example.moovie.presentation.auth.AuthViewModel
import com.example.moovie.ui.components.MoovieBottomBar
import com.example.moovie.ui.components.MoovieTopAppBar
import com.example.moovie.ui.navigation.MoovieNavHost
import com.example.moovie.ui.navigation.NavigationRoute
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainShell(
    navController: NavHostController = androidx.navigation.compose.rememberNavController()
) {
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    // Inject Koin AuthViewModel
    val authViewModel: AuthViewModel = koinViewModel()
    val authUiState by authViewModel.uiState.collectAsState()
    val sessionState by authViewModel.sessionState.collectAsState()

    // Determine whether to display the bottom navigation bar
    val showBottomBar = currentDestination.shouldShowBottomBar()

    // Determine whether to display the TopAppBar
    val showTopBar = currentDestination.shouldShowTopBar()

    // Determine the title of the TopAppBar dynamically via resource strings
    val title = currentDestination.getRouteTitle(currentBackStackEntry)

    Scaffold(
        topBar = {
            if (showTopBar) {
                MoovieTopAppBar(
                    title = title,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    onNavigateBack = { navController.navigateUp() }
                )
            }
        },
        bottomBar = {
            if (showBottomBar) {
                MoovieBottomBar(
                    currentDestination = currentDestination,
                    onNavigateToDestination = { route ->
                        navController.navigate(route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        MoovieNavHost(
            navController = navController,
            sessionState = sessionState,
            authUiState = authUiState,
            authViewModel = authViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

// Extension helper functions to simplify MainShell code
private fun NavDestination?.shouldShowBottomBar(): Boolean {
    if (this == null) return false
    return hasRoute<NavigationRoute.Home>() ||
           hasRoute<NavigationRoute.Search>() ||
           hasRoute<NavigationRoute.Watchlist>() ||
           hasRoute<NavigationRoute.Profile>()
}

private fun NavDestination?.shouldShowTopBar(): Boolean {
    if (this == null) return false
    return !hasRoute<NavigationRoute.Splash>() &&
           !hasRoute<NavigationRoute.Login>() &&
           !hasRoute<NavigationRoute.Register>() &&
           !hasRoute<NavigationRoute.Home>() &&
           !hasRoute<NavigationRoute.Search>() &&
           !hasRoute<NavigationRoute.Profile>()
}

@Composable
private fun NavDestination?.getRouteTitle(backStackEntry: NavBackStackEntry?): String {
    if (this == null) return stringResource(id = R.string.app_name)
    return when {
        hasRoute<NavigationRoute.Home>() -> stringResource(id = R.string.title_home)
        hasRoute<NavigationRoute.Search>() -> stringResource(id = R.string.title_search)
        hasRoute<NavigationRoute.Watchlist>() -> stringResource(id = R.string.title_watchlist)
        hasRoute<NavigationRoute.Profile>() -> stringResource(id = R.string.title_profile)
        hasRoute<NavigationRoute.Favorites>() -> stringResource(id = R.string.title_favorites)
        hasRoute<NavigationRoute.Settings>() -> stringResource(id = R.string.title_settings)
        hasRoute<NavigationRoute.Stats>() -> stringResource(id = R.string.title_stats)
        hasRoute<NavigationRoute.MovieExplorer>() -> stringResource(id = R.string.title_movie_explorer)
        hasRoute<NavigationRoute.Leaderboard>() -> stringResource(id = R.string.title_leaderboard)
        hasRoute<NavigationRoute.Detail>() -> {
            val movieId = backStackEntry?.toRoute<NavigationRoute.Detail>()?.movieId ?: 0
            stringResource(id = R.string.title_detail, movieId)
        }
        else -> stringResource(id = R.string.app_name)
    }
}
