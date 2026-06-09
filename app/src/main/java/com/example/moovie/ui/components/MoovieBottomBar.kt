package com.example.moovie.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.example.moovie.R
import com.example.moovie.ui.navigation.NavigationRoute

data class BottomNavItem(
    val route: NavigationRoute,
    val icon: ImageVector,
    val label: String,
    val isSelected: (NavDestination?) -> Boolean
)

@Composable
fun MoovieBottomBar(
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
