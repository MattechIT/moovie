package com.example.moovie.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.example.moovie.R
import com.example.moovie.presentation.watchlist.WatchlistViewModel
import com.example.moovie.ui.components.MovieListTemplate
import org.koin.androidx.compose.koinViewModel

/**
 * Screen displaying the user's movie watchlist from local storage.
 * Shows a beautiful empty state if no movies are saved.
 */
@Composable
fun WatchlistScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: WatchlistViewModel = koinViewModel()
) {
    val watchlistMovies by viewModel.watchlistMovies.collectAsState()

    MovieListTemplate(
        movies = watchlistMovies,
        isLoading = false,
        errorMessage = null,
        onMovieClick = onMovieClick,
        emptyIcon = Icons.Default.Bookmark,
        emptyTitle = stringResource(id = R.string.watchlist_empty_title),
        emptySubtitle = stringResource(id = R.string.watchlist_empty_subtitle)
    )
}
