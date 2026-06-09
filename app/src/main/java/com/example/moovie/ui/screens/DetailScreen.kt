package com.example.moovie.ui.screens

import android.content.Intent
import android.net.Uri

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.moovie.ui.components.MoovieNotificationBanner
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.moovie.R
import com.example.moovie.data.model.Genre
import com.example.moovie.presentation.detail.DetailViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.core.net.toUri
import com.example.moovie.util.startActivitySafe

/**
 * Detailed movie screen displaying backdrop, tagline, synopsis, cast details,
 * localized genre badges, and placeholders for external share/trailer action intents.
 */
@Composable
fun DetailScreen(
    movieId: Int,
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
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                    ) {
                        val backdropUrl = movie.backdropPath?.let { "https://image.tmdb.org/t/p/w780$it" }
                        
                        if (backdropUrl != null) {
                            SubcomposeAsyncImage(
                                model = backdropUrl,
                                contentDescription = movie.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                val state = painter.state
                                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                    Box(
                                        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceVariant)
                                    )
                                } else {
                                    SubcomposeAsyncImageContent()
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.surfaceVariant)
                            )
                        }

                        // Black gradient overlay to fade into dark background
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, MaterialTheme.colorScheme.background),
                                        startY = 100f
                                    )
                                )
                        )
                    }

                    // Poster, Title and Metadata row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .offset(y = (-40).dp),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        val posterUrl = movie.posterPath?.let { "https://image.tmdb.org/t/p/w500$it" }
                        Card(
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier
                                .size(width = 100.dp, height = 150.dp)
                                .background(Color.Transparent),
                            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.surfaceVariant),
                                contentAlignment = Alignment.Center
                            ) {
                                if (posterUrl != null) {
                                    SubcomposeAsyncImage(
                                        model = posterUrl,
                                        contentDescription = movie.title,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        val state = painter.state
                                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                                            Box(
                                                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surfaceVariant),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(text = "🎬", style = MaterialTheme.typography.headlineSmall)
                                            }
                                        } else {
                                            SubcomposeAsyncImageContent()
                                        }
                                    }
                                } else {
                                    Text(text = "🎬", style = MaterialTheme.typography.headlineSmall)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        // Movie titles and core meta details
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = movie.title,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground
                            )

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                if (movie.releaseDate.isNotBlank()) {
                                    Text(
                                        text = movie.releaseDate.substringBefore("-"),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(4.dp))
                                        .background(MaterialTheme.colorScheme.primaryContainer)
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.home_rating_format, movie.voteAverage),
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }

                                movie.runtime?.let { minutes ->
                                    val hours = minutes / 60
                                    val remainingMinutes = minutes % 60
                                    Text(
                                        text = stringResource(R.string.detail_duration_format, hours, remainingMinutes),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                    )
                                }
                            }
                        }
                    }

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
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth().padding(bottom = 20.dp)
                        ) {
                            // Trailer Intent
                            Button(
                                onClick = {
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
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary
                                ),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = stringResource(R.string.detail_trailer_button),
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            // Favorite Icon Button
                            IconButton(
                                onClick = {
                                    viewModel.toggleFavorite()
                                    val msgRes = if (uiState.isFavorite) R.string.detail_fav_removed else R.string.detail_fav_added
                                    bannerMessage = context.getString(msgRes)
                                    isErrorBanner = false
                                },
                                modifier = Modifier.clip(CircleShape).background(MaterialTheme.colorScheme.surface)
                            ) {
                                Icon(
                                    imageVector = if (uiState.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = stringResource(R.string.detail_fav_description),
                                    tint = if (uiState.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface
                                )
                            }

                            // Watchlist Icon Button
                            IconButton(
                                onClick = {
                                    viewModel.toggleSaved()
                                    val msgRes = if (uiState.isSaved) R.string.detail_watchlist_removed else R.string.detail_watchlist_added
                                    bannerMessage = context.getString(msgRes)
                                    isErrorBanner = false
                                },
                                modifier = Modifier.clip(CircleShape).background(MaterialTheme.colorScheme.surface)
                            ) {
                                Icon(
                                    imageVector = if (uiState.isSaved) Icons.Default.Check else Icons.Default.Add,
                                    contentDescription = stringResource(R.string.detail_watchlist_description),
                                    tint = if (uiState.isSaved) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            }

                            // Share Intent
                            IconButton(
                                onClick = {
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
                                },
                                modifier = Modifier.clip(CircleShape).background(MaterialTheme.colorScheme.surface)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = stringResource(R.string.detail_share_description),
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

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

                        if (movie.combinedGenreIds.isNotEmpty()) {
                            Text(
                                text = stringResource(R.string.detail_genres_title),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onBackground,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
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
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}
