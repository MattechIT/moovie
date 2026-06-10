package com.example.moovie.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.example.moovie.R
import com.example.moovie.presentation.favorites.FavoritesViewModel
import com.example.moovie.ui.components.MovieListTemplate
import org.koin.androidx.compose.koinViewModel

/**
 * Screen displaying the user's favorite movies from local storage.
 * Shows a beautiful empty state if no movies are marked as favorite.
 */
@Composable
fun FavoritesScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: FavoritesViewModel = koinViewModel()
) {
    val favoriteMovies by viewModel.favoriteMovies.collectAsState()

    MovieListTemplate(
        movies = favoriteMovies,
        isLoading = false,
        errorMessage = null,
        onMovieClick = onMovieClick,
        emptyIcon = Icons.Default.Favorite,
        emptyTitle = stringResource(id = R.string.favorites_empty_title),
        emptySubtitle = stringResource(id = R.string.favorites_empty_subtitle)
    )
}
