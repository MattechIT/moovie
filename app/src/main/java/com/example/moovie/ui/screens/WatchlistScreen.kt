package com.example.moovie.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
