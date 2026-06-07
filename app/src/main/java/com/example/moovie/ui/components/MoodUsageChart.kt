package com.example.moovie.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moovie.R
import com.example.moovie.data.model.Mood

/**
 * Customized horizontal bar chart visualizing mood selection preferences.
 */
@Composable
fun MoodUsageChart(
    moodUsageDistribution: List<Pair<Mood, Int>>,
    modifier: Modifier = Modifier
) {
    val maxCount = moodUsageDistribution.maxOfOrNull { it.second } ?: 1

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = R.string.stats_mood_stats_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Show all moods that have been used at least once
            moodUsageDistribution.filter { it.second > 0 }.forEach { (mood, count) ->
                val targetFraction = count.toFloat() / maxCount.coerceAtLeast(1)

                var animationStarted by remember { mutableStateOf(false) }
                val animatedFraction by animateFloatAsState(
                    targetValue = if (animationStarted) targetFraction else 0f,
                    animationSpec = tween(durationMillis = 1000),
                    label = "mood_bar"
                )

                LaunchedEffect(Unit) {
                    animationStarted = true
                }

                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = mood.emoji, fontSize = 16.sp)
                            Text(
                                text = stringResource(id = mood.titleRes),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        Text(
                            text = count.toString(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = mood.colorAccent
                        )
                    }

                    // Horizontal progress bar
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(animatedFraction)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(4.dp))
                                .background(mood.colorAccent)
                        )
                    }
                }
            }
        }
    }
}
