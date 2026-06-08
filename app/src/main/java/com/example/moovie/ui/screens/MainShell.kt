package com.example.moovie.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.moovie.R
import com.example.moovie.presentation.auth.AuthViewModel
import com.example.moovie.ui.navigation.NavigationRoute
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

private data class BottomNavItem(
    val route: NavigationRoute,
    val icon: ImageVector,
    val label: String,
    val isSelected: (NavDestination?) -> Boolean
)

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
    val showBottomBar = currentDestination?.let { dest ->
        dest.hasRoute<NavigationRoute.Home>() ||
        dest.hasRoute<NavigationRoute.Search>() ||
        dest.hasRoute<NavigationRoute.Watchlist>() ||
        dest.hasRoute<NavigationRoute.Profile>()
    } ?: false

    // Determine whether to display the TopAppBar
    val showTopBar = currentDestination?.let { dest ->
        !dest.hasRoute<NavigationRoute.Splash>() &&
        !dest.hasRoute<NavigationRoute.Login>() &&
        !dest.hasRoute<NavigationRoute.Register>() &&
        !dest.hasRoute<NavigationRoute.Home>() &&
        !dest.hasRoute<NavigationRoute.Search>() &&
        !dest.hasRoute<NavigationRoute.Profile>()
    } ?: false

    // Determine the title of the TopAppBar dynamically via resource strings
    val title = when {
        currentDestination?.hasRoute<NavigationRoute.Home>() == true -> stringResource(id = R.string.title_home)
        currentDestination?.hasRoute<NavigationRoute.Search>() == true -> stringResource(id = R.string.title_search)
        currentDestination?.hasRoute<NavigationRoute.Watchlist>() == true -> stringResource(id = R.string.title_watchlist)
        currentDestination?.hasRoute<NavigationRoute.Profile>() == true -> stringResource(id = R.string.title_profile)
        currentDestination?.hasRoute<NavigationRoute.Favorites>() == true -> stringResource(id = R.string.title_favorites)
        currentDestination?.hasRoute<NavigationRoute.Settings>() == true -> stringResource(id = R.string.title_settings)
        currentDestination?.hasRoute<NavigationRoute.Stats>() == true -> stringResource(id = R.string.title_stats)
        currentDestination?.hasRoute<NavigationRoute.MovieExplorer>() == true -> stringResource(id = R.string.title_movie_explorer)
        currentDestination?.hasRoute<NavigationRoute.Detail>() == true -> {
            val movieId = currentBackStackEntry?.toRoute<NavigationRoute.Detail>()?.movieId ?: 0
            stringResource(id = R.string.title_detail, movieId)
        }
        else -> stringResource(id = R.string.app_name)
    }

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
        NavHost(
            navController = navController,
            startDestination = NavigationRoute.Splash,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<NavigationRoute.Splash> {
                // Auto-route based on session state at launch
                LaunchedEffect(sessionState.isAuthenticated) {
                    delay(1500)
                    if (navController.currentDestination?.hasRoute<NavigationRoute.Splash>() == true) {
                        if (sessionState.isAuthenticated) {
                            navController.navigate(NavigationRoute.Home) {
                                popUpTo(NavigationRoute.Splash) { inclusive = true }
                            }
                        } else {
                            navController.navigate(NavigationRoute.Login) {
                                popUpTo(NavigationRoute.Splash) { inclusive = true }
                            }
                        }
                    }
                }
                SplashScreen()
            }
            
            composable<NavigationRoute.Login> {
                // Reset UI State on entry
                LaunchedEffect(Unit) {
                    authViewModel.resetState()
                }
                
                LoginScreen(
                    email = authViewModel.email,
                    password = authViewModel.password,
                    uiState = authUiState,
                    onEmailChanged = authViewModel::updateEmail,
                    onPasswordChanged = authViewModel::updatePassword,
                    onLoginClick = {
                        authViewModel.login(
                            onSuccess = {
                                // Clear back stack to prevent returning to login after successful authentication
                                navController.navigate(NavigationRoute.Home) {
                                    popUpTo(navController.graph.id) { inclusive = true }
                                }
                            }
                        )
                    },
                    onNavigateToRegister = {
                        navController.navigate(NavigationRoute.Register)
                    }
                )
            }
            
            composable<NavigationRoute.Register> {
                // Reset UI State on entry
                LaunchedEffect(Unit) {
                    authViewModel.resetState()
                }
                
                RegisterScreen(
                    email = authViewModel.email,
                    password = authViewModel.password,
                    uiState = authUiState,
                    onEmailChanged = authViewModel::updateEmail,
                    onPasswordChanged = authViewModel::updatePassword,
                    onRegisterClick = {
                        authViewModel.register(
                            onSuccess = {
                                // Show confirmation card
                            }
                        )
                    },
                    onNavigateToLogin = {
                        navController.popBackStack()
                    }
                )
            }
            
            composable<NavigationRoute.Home> {
                HomeScreen(
                    onNavigateToDetail = { movieId ->
                        navController.navigate(NavigationRoute.Detail(movieId))
                    }
                )
            }
            
            composable<NavigationRoute.Search> {
                SearchScreen(
                    onMovieClick = { movieId ->
                        navController.navigate(NavigationRoute.Detail(movieId))
                    }
                )
            }
            
            composable<NavigationRoute.Watchlist> {
                WatchlistScreen(
                    onMovieClick = { movieId ->
                        navController.navigate(NavigationRoute.Detail(movieId))
                    }
                )
            }
            
            composable<NavigationRoute.Profile> {
                ProfileScreen(
                    onNavigateToFavorites = {
                        navController.navigate(NavigationRoute.Favorites)
                    },
                    onNavigateToWatchlist = {
                        navController.navigate(NavigationRoute.Watchlist)
                    },
                    onNavigateToSettings = {
                        navController.navigate(NavigationRoute.Settings)
                    },
                    onNavigateToStats = {
                        navController.navigate(NavigationRoute.Stats)
                    },
                    onNavigateToMovieExplorer = {
                        navController.navigate(NavigationRoute.MovieExplorer)
                    },
                    onLogout = {
                        authViewModel.logout(
                            onComplete = {
                                navController.navigate(NavigationRoute.Login) {
                                    popUpTo(0) { inclusive = true }
                                }
                            }
                        )
                    }
                )
            }
            
            composable<NavigationRoute.Favorites> {
                FavoritesScreen(
                    onMovieClick = { movieId ->
                        navController.navigate(NavigationRoute.Detail(movieId))
                    }
                )
            }
            
            composable<NavigationRoute.Settings> {
                SettingsScreen()
            }
            
            composable<NavigationRoute.Stats> {
                StatsScreen()
            }
            
            composable<NavigationRoute.MovieExplorer> {
                MovieExplorerScreen(
                    onMovieClick = { movieId ->
                        navController.navigate(NavigationRoute.Detail(movieId))
                    }
                )
            }
            
            composable<NavigationRoute.Detail>(
                deepLinks = listOf(
                    navDeepLink { uriPattern = "moovie://details/{movieId}" }
                )
            ) { backStackEntry ->
                val route = backStackEntry.toRoute<NavigationRoute.Detail>()
                DetailScreen(movieId = route.movieId)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoovieTopAppBar(
    title: String,
    canNavigateBack: Boolean,
    onNavigateBack: () -> Unit
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button_description)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground,
            navigationIconContentColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Composable
private fun MoovieBottomBar(
    currentDestination: NavDestination?,
    onNavigateToDestination: (NavigationRoute) -> Unit
) {
    val items = listOf(
        BottomNavItem(
            route = NavigationRoute.Home,
            icon = Icons.Default.Home,
            label = stringResource(id = R.string.nav_home),
            isSelected = { it?.hasRoute<NavigationRoute.Home>() == true }
        ),
        BottomNavItem(
            route = NavigationRoute.Search,
            icon = Icons.Default.Search,
            label = stringResource(id = R.string.nav_search),
            isSelected = { it?.hasRoute<NavigationRoute.Search>() == true }
        ),
        BottomNavItem(
            route = NavigationRoute.Watchlist,
            icon = Icons.AutoMirrored.Filled.List,
            label = stringResource(id = R.string.nav_watchlist),
            isSelected = { it?.hasRoute<NavigationRoute.Watchlist>() == true }
        ),
        BottomNavItem(
            route = NavigationRoute.Profile,
            icon = Icons.Default.Person,
            label = stringResource(id = R.string.nav_profile),
            isSelected = { it?.hasRoute<NavigationRoute.Profile>() == true }
        )
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.primary
    ) {
        items.forEach { item ->
            NavigationBarItem(
                selected = item.isSelected(currentDestination),
                onClick = { onNavigateToDestination(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}
