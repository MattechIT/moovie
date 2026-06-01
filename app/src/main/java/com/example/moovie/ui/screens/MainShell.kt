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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.moovie.ui.navigation.NavigationRoute
import kotlinx.coroutines.delay

private data class BottomNavItem(
    val route: NavigationRoute,
    val icon: ImageVector,
    val label: String,
    val isSelected: (NavDestination?) -> Boolean
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainShell() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    // Show BottomBar only for Home, Search, Watchlist e Profile
    val showBottomBar = currentDestination?.let { dest ->
        dest.hasRoute<NavigationRoute.Home>() ||
        dest.hasRoute<NavigationRoute.Search>() ||
        dest.hasRoute<NavigationRoute.Watchlist>() ||
        dest.hasRoute<NavigationRoute.Profile>()
    } ?: false

    // Hide TopBar for Splash, Login e Register
    val showTopBar = currentDestination?.let { dest ->
        !dest.hasRoute<NavigationRoute.Splash>() &&
        !dest.hasRoute<NavigationRoute.Login>() &&
        !dest.hasRoute<NavigationRoute.Register>()
    } ?: false

    val title = when {
        currentDestination?.hasRoute<NavigationRoute.Home>() == true -> "Moovie Feed"
        currentDestination?.hasRoute<NavigationRoute.Search>() == true -> "Cerca / Mood"
        currentDestination?.hasRoute<NavigationRoute.Watchlist>() == true -> "La tua Watchlist"
        currentDestination?.hasRoute<NavigationRoute.Profile>() == true -> "Profilo Utente"
        currentDestination?.hasRoute<NavigationRoute.Favorites>() == true -> "Film Preferiti"
        currentDestination?.hasRoute<NavigationRoute.Settings>() == true -> "Impostazioni"
        currentDestination?.hasRoute<NavigationRoute.Stats>() == true -> "Statistiche personali"
        currentDestination?.hasRoute<NavigationRoute.MovieExplorer>() == true -> "Movie Explorer"
        currentDestination?.hasRoute<NavigationRoute.Detail>() == true -> "Dettaglio Film"
        else -> "Moovie"
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
                LaunchedEffect(Unit) {
                    delay(1500)
                    navController.navigate(NavigationRoute.Login) {
                        popUpTo(NavigationRoute.Splash) { inclusive = true }
                    }
                }
                SplashScreen()
            }
            
            composable<NavigationRoute.Login> {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(NavigationRoute.Home) {
                            popUpTo(NavigationRoute.Login) { inclusive = true }
                        }
                    },
                    onNavigateToRegister = {
                        navController.navigate(NavigationRoute.Register)
                    }
                )
            }
            
            composable<NavigationRoute.Register> {
                RegisterScreen(
                    onRegisterSuccess = {
                        navController.navigate(NavigationRoute.Home) {
                            popUpTo(NavigationRoute.Register) { inclusive = true }
                        }
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
                SearchScreen()
            }
            
            composable<NavigationRoute.Watchlist> {
                WatchlistScreen()
            }
            
            composable<NavigationRoute.Profile> {
                ProfileScreen(
                    onNavigateToFavorites = {
                        navController.navigate(NavigationRoute.Favorites)
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
                        navController.navigate(NavigationRoute.Login) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
            
            composable<NavigationRoute.Favorites> {
                FavoritesScreen()
            }
            
            composable<NavigationRoute.Settings> {
                SettingsScreen()
            }
            
            composable<NavigationRoute.Stats> {
                StatsScreen()
            }
            
            composable<NavigationRoute.MovieExplorer> {
                MovieExplorerScreen()
            }
            
            composable<NavigationRoute.Detail> { backStackEntry ->
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
                        contentDescription = "Torna indietro"
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
            label = "Home",
            isSelected = { it?.hasRoute<NavigationRoute.Home>() == true }
        ),
        BottomNavItem(
            route = NavigationRoute.Search,
            icon = Icons.Default.Search,
            label = "Cerca",
            isSelected = { it?.hasRoute<NavigationRoute.Search>() == true }
        ),
        BottomNavItem(
            route = NavigationRoute.Watchlist,
            icon = Icons.AutoMirrored.Filled.List,
            label = "Watchlist",
            isSelected = { it?.hasRoute<NavigationRoute.Watchlist>() == true }
        ),
        BottomNavItem(
            route = NavigationRoute.Profile,
            icon = Icons.Default.Person,
            label = "Profilo",
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
