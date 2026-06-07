package com.example.moovie.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moovie.R
import com.example.moovie.data.model.Genre
import com.example.moovie.data.model.Mood
import com.example.moovie.presentation.stats.StatsViewModel
import com.example.moovie.ui.components.GenreDistributionChart
import com.example.moovie.ui.components.MetricSummaryCard
import com.example.moovie.ui.components.MoodUsageChart
import org.koin.androidx.compose.koinViewModel

/**
 * Premium Statistics & Charts Screen.
 * Visualizes user's personal movie metrics, favorite genres, and mood selection habits.
 */
@Composable
fun StatsScreen(
    viewModel: StatsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        } else {
            val hasSavedMovies = uiState.totalUniqueSaved > 0
            val hasMoodClicks = uiState.moodUsageDistribution.any { it.second > 0 }

            if (!hasSavedMovies && !hasMoodClicks) {
                StatsEmptyState(modifier = Modifier.fillMaxSize())
            } else {
                StatsContent(
                    totalFavorites = uiState.totalFavorites,
                    totalWatchlist = uiState.totalWatchlist,
                    totalUniqueSaved = uiState.totalUniqueSaved,
                    totalWatchTimeMinutes = uiState.totalWatchTimeMinutes,
                    genreDistribution = uiState.genreDistribution,
                    moodUsageDistribution = uiState.moodUsageDistribution,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

/**
 * Empty state layout shown when no stats data has been collected yet.
 */
@Composable
private fun StatsEmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "📊",
            fontSize = 64.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = stringResource(id = R.string.stats_no_data_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = stringResource(id = R.string.stats_no_data_subtitle),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}

/**
 * Main dashboard displaying visual metrics and charts.
 */
@Composable
private fun StatsContent(
    totalFavorites: Int,
    totalWatchlist: Int,
    totalUniqueSaved: Int,
    totalWatchTimeMinutes: Int,
    genreDistribution: List<Pair<Genre, Int>>,
    moodUsageDistribution: List<Pair<Mood, Int>>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(28.dp)
    ) {
        // Grid-like metric Cards section
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                MetricSummaryCard(
                    title = stringResource(id = R.string.stats_movies_saved_title),
                    value = totalUniqueSaved.toString(),
                    iconEmoji = "🎬",
                    modifier = Modifier.weight(1f)
                )
                MetricSummaryCard(
                    title = stringResource(id = R.string.stats_total_watchtime),
                    value = formatWatchTime(totalWatchTimeMinutes),
                    iconEmoji = "⏱️",
                    modifier = Modifier.weight(1f)
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                MetricSummaryCard(
                    title = stringResource(id = R.string.stats_total_favorites),
                    value = totalFavorites.toString(),
                    iconEmoji = "❤️",
                    modifier = Modifier.weight(1f)
                )
                MetricSummaryCard(
                    title = stringResource(id = R.string.stats_total_watchlist),
                    value = totalWatchlist.toString(),
                    iconEmoji = "⏳",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // Top Genres distribution custom chart
        if (genreDistribution.isNotEmpty()) {
            GenreDistributionChart(genreDistribution = genreDistribution)
        }


        // Mood usage custom chart
        if (moodUsageDistribution.any { it.second > 0 }) {
            MoodUsageChart(moodUsageDistribution = moodUsageDistribution)
        }
    }
}

/**
 * Format total minutes to a string representation.
 */
@Composable
private fun formatWatchTime(minutes: Int): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return if (hours > 0) {
        stringResource(id = R.string.detail_duration_format, hours, remainingMinutes)
    } else {
        "$remainingMinutes " + stringResource(id = R.string.stats_minutes)
    }
}
