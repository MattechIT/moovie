package com.example.moovie.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.toRoute
import com.example.moovie.data.model.UserSession
import com.example.moovie.data.repository.PreferenceRepository
import com.example.moovie.platform.biometric.BiometricService
import com.example.moovie.presentation.auth.AuthUiState
import com.example.moovie.presentation.auth.AuthViewModel
import com.example.moovie.ui.screens.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import org.koin.compose.koinInject

@Composable
fun MoovieNavHost(
    navController: NavHostController,
    sessionState: UserSession,
    authUiState: AuthUiState,
    authViewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val preferenceRepository: PreferenceRepository = koinInject()
    val biometricService: BiometricService = koinInject()

    NavHost(
        navController = navController,
        startDestination = NavigationRoute.Splash,
        modifier = modifier
    ) {
        composable<NavigationRoute.Splash>(
            deepLinks = listOf(
                navDeepLink { uriPattern = "moovie://app" }
            )
        ) {
            // Auto-route based on session state at launch
            LaunchedEffect(sessionState.isAuthenticated) {
                delay(1500)
                if (navController.currentDestination?.hasRoute<NavigationRoute.Splash>() == true) {
                    if (sessionState.isAuthenticated) {
                        // Check if biometric lock is enabled and available
                        val isBiometricEnabled = preferenceRepository.biometricLockEnabled.first()
                        val isBiometricAvailable = biometricService.isBiometricAvailable()

                        if (isBiometricEnabled && isBiometricAvailable) {
                            navController.navigate(NavigationRoute.BiometricUnlock) {
                                popUpTo(NavigationRoute.Splash) { inclusive = true }
                            }
                        } else {
                            navController.navigate(NavigationRoute.Home) {
                                popUpTo(NavigationRoute.Splash) { inclusive = true }
                            }
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

        composable<NavigationRoute.BiometricUnlock> {
            BiometricUnlockScreen(
                onUnlockSuccess = {
                    navController.navigate(NavigationRoute.Home) {
                        popUpTo(NavigationRoute.BiometricUnlock) { inclusive = true }
                    }
                }
            )
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
                onNavigateToLeaderboard = {
                    navController.navigate(NavigationRoute.Leaderboard)
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
        
        composable<NavigationRoute.Leaderboard> {
            LeaderboardScreen()
        }

        composable<NavigationRoute.ActorMovies> { backStackEntry ->
            val route = backStackEntry.toRoute<NavigationRoute.ActorMovies>()
            ActorMoviesScreen(
                actorId = route.actorId,
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
            
            LaunchedEffect(sessionState.isInitializing, sessionState.isAuthenticated) {
                if (!sessionState.isInitializing && !sessionState.isAuthenticated) {
                    navController.navigate(NavigationRoute.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            }

            if (sessionState.isInitializing) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            } else if (sessionState.isAuthenticated) {
                DetailScreen(
                    movieId = route.movieId,
                    onActorClick = { actorId, actorName ->
                        navController.navigate(NavigationRoute.ActorMovies(actorId, actorName))
                    }
                )
            }
        }
    }
}
