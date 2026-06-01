package com.example.moovie.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moovie.R

@Composable
fun ProfileScreen(
    onNavigateToFavorites: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToMovieExplorer: () -> Unit,
    onLogout: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.screen_profile),
            color = MaterialTheme.colorScheme.onBackground
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = stringResource(id = R.string.profile_view_favorites),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onNavigateToFavorites() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.profile_view_settings),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onNavigateToSettings() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.profile_view_stats),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onNavigateToStats() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.profile_open_explorer),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onNavigateToMovieExplorer() }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = stringResource(id = R.string.profile_logout),
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.clickable { onLogout() }
        )
    }
}
