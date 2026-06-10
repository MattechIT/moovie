package com.example.moovie.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moovie.R
import com.example.moovie.data.model.Movie

/**
 * Layout template for displaying vertical lists of movies.
 * Encapsulates common list states: Loading, Error, Empty, and Success.
 */
@Composable
fun MovieListTemplate(
    movies: List<Movie>,
    isLoading: Boolean,
    errorMessage: String?,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    // Styling & Highlight Colors
    colorAccent: Color = MaterialTheme.colorScheme.primary,
    // Pagination & Infinite Scroll parameters
    isLoadingMore: Boolean = false,
    onLoadMore: (() -> Unit)? = null,
    // Empty state customization
    emptyIcon: ImageVector? = null,
    emptyEmoji: String? = null,
    emptyTitle: String = "",
    emptySubtitle: String = "",
    // Error & Loading customization
    onRetry: (() -> Unit)? = null,
    loadingText: String? = null
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when {
            isLoading -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = colorAccent,
                        strokeWidth = 4.dp
                    )
                    if (loadingText != null) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = loadingText,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }

            errorMessage != null -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "⚠️",
                        fontSize = 64.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    Text(
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                    if (onRetry != null) {
                        Button(
                            onClick = onRetry,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorAccent
                            )
                        ) {
                            Text(text = stringResource(id = R.string.search_retry))
                        }
                    }
                }
            }

            movies.isEmpty() -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (emptyEmoji != null) {
                        Text(
                            text = emptyEmoji,
                            fontSize = 64.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    } else if (emptyIcon != null) {
                        Icon(
                            imageVector = emptyIcon,
                            contentDescription = null,
                            tint = colorAccent.copy(alpha = 0.6f),
                            modifier = Modifier.size(72.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    if (emptyTitle.isNotBlank()) {
                        Text(
                            text = emptyTitle,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center
                        )
                    }

                    if (emptySubtitle.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = emptySubtitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                            textAlign = TextAlign.Center,
                            lineHeight = 20.sp
                        )
                    }
                }
            }

            else -> {
                val listState = rememberLazyListState()

                if (onLoadMore != null) {
                    val shouldLoadMore = remember {
                        derivedStateOf {
                            val totalItems = listState.layoutInfo.totalItemsCount
                            val lastVisibleItem = listState.layoutInfo.visibleItemsInfo.lastOrNull()
                            if (lastVisibleItem == null) {
                                false
                            } else {
                                lastVisibleItem.index >= totalItems - 4
                            }
                        }
                    }

                    LaunchedEffect(shouldLoadMore.value) {
                        if (shouldLoadMore.value) {
                            onLoadMore()
                        }
                    }
                }

                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(movies, key = { it.id }) { movie ->
                        MovieCard(
                            movie = movie,
                            onClick = { onMovieClick(movie.id) },
                            moodColorAccent = colorAccent
                        )
                    }

                    if (isLoadingMore) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = colorAccent,
                                    strokeWidth = 3.dp,
                                    modifier = Modifier.size(32.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
