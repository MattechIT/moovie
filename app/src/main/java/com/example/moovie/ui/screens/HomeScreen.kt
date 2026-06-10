package com.example.moovie.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moovie.R
import com.example.moovie.presentation.home.HomeViewModel
import com.example.moovie.ui.components.MoodSelectorDialog
import com.example.moovie.ui.components.MoodSelectorHeader
import com.example.moovie.ui.components.MovieListTemplate
import org.koin.androidx.compose.koinViewModel

/**
 * Main Home Feed screen displaying movie suggestions categorized by the selected mood.
 */
@Composable
fun HomeScreen(
    onNavigateToDetail: (Int) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val showMoodSelector = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        MoodSelectorHeader(
            selectedMood = uiState.selectedMood,
            onOpenSelector = { showMoodSelector.value = true }
        )

        HorizontalDivider(
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Movie Feed Content
        MovieListTemplate(
            movies = uiState.movies,
            isLoading = uiState.isLoading,
            errorMessage = if (uiState.errorMessage != null) stringResource(id = R.string.home_error_connection) else null,
            onMovieClick = onNavigateToDetail,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            colorAccent = uiState.selectedMood.colorAccent,
            isLoadingMore = uiState.isLoadingMore,
            onLoadMore = { viewModel.loadNextPage() },
            emptyEmoji = "🍿",
            emptyTitle = stringResource(R.string.home_no_movies_found),
            onRetry = { viewModel.retryFetch() },
            loadingText = stringResource(R.string.home_loading),
            errorTitle = stringResource(id = R.string.home_error_title)
        )
    }

    if (showMoodSelector.value) {
        MoodSelectorDialog(
            selectedMood = uiState.selectedMood,
            onMoodSelected = { mood -> viewModel.selectMood(mood) },
            onDismiss = { showMoodSelector.value = false }
        )
    }
}
