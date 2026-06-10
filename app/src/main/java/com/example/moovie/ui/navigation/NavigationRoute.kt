package com.example.moovie.ui.navigation

import kotlinx.serialization.Serializable

sealed interface NavigationRoute {
    
    @Serializable
    data object Splash : NavigationRoute
    
    @Serializable
    data object Login : NavigationRoute
    
    @Serializable
    data object Register : NavigationRoute
    
    @Serializable
    data object Home : NavigationRoute
    
    @Serializable
    data object Search : NavigationRoute
    
    @Serializable
    data object Watchlist : NavigationRoute
    
    @Serializable
    data object Favorites : NavigationRoute
    
    @Serializable
    data object Profile : NavigationRoute
    
    @Serializable
    data object Settings : NavigationRoute
    
    @Serializable
    data object Stats : NavigationRoute
    
    @Serializable
    data class Detail(val movieId: Int) : NavigationRoute
    
    @Serializable
    data object MovieExplorer : NavigationRoute

    @Serializable
    data object Leaderboard : NavigationRoute

    @Serializable
    data object BiometricUnlock : NavigationRoute

    @Serializable
    data class ActorMovies(val actorId: Int, val actorName: String) : NavigationRoute
}
