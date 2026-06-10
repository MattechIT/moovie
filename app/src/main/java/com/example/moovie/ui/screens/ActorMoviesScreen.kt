package com.example.moovie.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.example.moovie.R
import com.example.moovie.presentation.actor.ActorMoviesViewModel
import com.example.moovie.ui.components.MovieListTemplate
import org.koin.androidx.compose.koinViewModel

/**
 * Screen displaying the list of movies featuring a specific actor.
 * Automatically fetches pages when scrolling near the bottom.
 */
@Composable
fun ActorMoviesScreen(
    actorId: Int,
    onMovieClick: (Int) -> Unit,
    viewModel: ActorMoviesViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(actorId) {
        viewModel.loadMovies(actorId)
    }

    MovieListTemplate(
        movies = uiState.movies,
        isLoading = uiState.isLoading,
        errorMessage = if (uiState.errorMessage != null) stringResource(id = R.string.home_error_connection) else null,
        onMovieClick = onMovieClick,
        isLoadingMore = uiState.isLoadingMore,
        onLoadMore = { viewModel.loadNextPage(actorId) },
        emptyIcon = Icons.Default.Star,
        emptyTitle = stringResource(id = R.string.actor_movies_empty_title),
        emptySubtitle = stringResource(id = R.string.actor_movies_empty_subtitle),
        onRetry = { viewModel.retryFetch(actorId) }
    )
}
