package com.example.moovie.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.example.moovie.R
import com.example.moovie.data.model.Genre
import com.example.moovie.presentation.detail.DetailViewModel
import com.example.moovie.ui.components.CastMemberItem
import com.example.moovie.ui.components.DetailActionsRow
import com.example.moovie.ui.components.DetailBackdropHeader
import com.example.moovie.ui.components.DetailMetadataHeader
import com.example.moovie.ui.components.MoovieNotificationBanner
import com.example.moovie.util.startActivitySafe
import org.koin.androidx.compose.koinViewModel

/**
 * Detailed movie screen displaying backdrop, tagline, synopsis, cast details,
 * localized genre badges, and placeholders for external share/trailer action intents.
 */
@Composable
fun DetailScreen(
    movieId: Int,
    onActorClick: (Int, String) -> Unit,
    viewModel: DetailViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    var bannerMessage by remember { mutableStateOf<String?>(null) }
    var isErrorBanner by remember { mutableStateOf(false) }

    LaunchedEffect(movieId) {
        viewModel.loadMovie(movieId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            uiState.errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = uiState.errorMessage ?: stringResource(R.string.detail_error_failed),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Button(onClick = { viewModel.loadMovie(movieId) }) {
                        Text(text = stringResource(R.string.detail_retry))
                    }
                }
            }
            uiState.movie != null -> {
                val movie = uiState.movie!!
                val scrollState = rememberScrollState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(scrollState)
                ) {
                    // Backdrop Header
                    DetailBackdropHeader(
                        backdropPath = movie.backdropPath,
                        title = movie.title
                    )

                    // Poster, Title and Metadata row
                    val directorName = movie.credits?.crew?.firstOrNull { it.job == "Director" }?.name
                    DetailMetadataHeader(
                        posterPath = movie.posterPath,
                        title = movie.title,
                        releaseDate = movie.releaseDate,
                        voteAverage = movie.voteAverage,
                        runtime = movie.runtime,
                        directorName = directorName
                    )

                    // Synopsis, Tagline, Actions and Genres
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .offset(y = (-20).dp)
                    ) {
                        movie.tagline?.let { tag ->
                            if (tag.isNotBlank()) {
                                Text(
                                    text = "\"$tag\"",
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontStyle = FontStyle.Italic,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(bottom = 16.dp)
                                )
                            }
                        }

                        // Action Buttons Row
                        DetailActionsRow(
                            isFavorite = uiState.isFavorite,
                            isSaved = uiState.isSaved,
                            onTrailerClick = {
                                val year = if (movie.releaseDate.isNotBlank()) " " + movie.releaseDate.substringBefore("-") else ""
                                val queryUrl = "https://www.youtube.com/results?search_query=trailer+${Uri.encode(movie.title + year)}"
                                val trailerIntent = Intent(
                                    Intent.ACTION_VIEW,
                                    queryUrl.toUri()
                                )
                                try {
                                    context.startActivitySafe(trailerIntent)
                                } catch (_: Exception) {
                                    bannerMessage = context.getString(R.string.detail_error_failed)
                                    isErrorBanner = true
                                }
                            },
                            onFavoriteClick = {
                                viewModel.toggleFavorite()
                                val msgRes = if (uiState.isFavorite) R.string.detail_fav_removed else R.string.detail_fav_added
                                bannerMessage = context.getString(msgRes)
                                isErrorBanner = false
                            },
                            onSavedClick = {
                                viewModel.toggleSaved()
                                val msgRes = if (uiState.isSaved) R.string.detail_watchlist_removed else R.string.detail_watchlist_added
                                bannerMessage = context.getString(msgRes)
                                isErrorBanner = false
                            },
                            onShareClick = {
                                val shareText = context.getString(
                                    R.string.detail_share_text,
                                    movie.title,
                                    movie.tagline?.let { if (it.isNotBlank()) "\"$it\"" else "" } ?: ""
                                ) + "\n\nmoovie://details/${movie.id}"
                                val sendIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, shareText)
                                    type = "text/plain"
                                }
                                val shareIntent = Intent.createChooser(sendIntent, null)
                                context.startActivitySafe(shareIntent)
                            }
                        )

                        Text(
                            text = stringResource(R.string.detail_synopsis_title),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        Text(
                            text = movie.overview,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 22.sp,
                            modifier = Modifier.padding(bottom = 20.dp)
                        )

                        // Main Cast Section
                        val cast = movie.credits?.cast
                        if (!cast.isNullOrEmpty()) {
                            Text(
                                text = stringResource(R.string.detail_cast_title),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            LazyRow(
                                horizontalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 24.dp)
                            ) {
                                items(cast, key = { it.id }) { actor ->
                                    CastMemberItem(
                                        actor = actor,
                                        onClick = { onActorClick(actor.id, actor.name) }
                                    )
                                }
                            }
                        }

                        if (movie.combinedGenreIds.isNotEmpty()) {
                            Text(
                                text = stringResource(R.string.detail_genres_title),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            FlowRow(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                movie.combinedGenreIds.forEach { genreId ->
                                    val genre = Genre.fromId(genreId)
                                    if (genre != null) {
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                        ) {
                                            Text(
                                                text = stringResource(id = genre.titleRes),
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Animated notification banner for warnings/success feedback
        MoovieNotificationBanner(
            visible = bannerMessage != null,
            message = bannerMessage ?: "",
            onDismiss = { bannerMessage = null },
            containerColor = if (isErrorBanner) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer,
            contentColor = if (isErrorBanner) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}
