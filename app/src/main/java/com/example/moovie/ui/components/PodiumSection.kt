package com.example.moovie.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import androidx.compose.foundation.clickable
import com.example.moovie.data.model.LeaderboardUser

/**
 * Podium section that displays the top 3 users in a stylized podium layout.
 */
@Composable
fun PodiumSection(
    topThree: List<LeaderboardUser>,
    onUserClick: (Int, LeaderboardUser) -> Unit,
    modifier: Modifier = Modifier
) {
    // Reorder list to put 2nd on left, 1st in middle, 3rd on right
    val podiumOrder = when (topThree.size) {
        1 -> listOf(null, topThree[0], null)
        2 -> listOf(topThree[1], topThree[0], null)
        else -> listOf(topThree[1], topThree[0], topThree[2])
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .border(
                1.dp,
                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.05f),
                RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        podiumOrder.forEach { user ->
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.BottomCenter
            ) {
                if (user != null) {
                    val rank = when (user.id) {
                        topThree[0].id -> 1
                        topThree.getOrNull(1)?.id -> 2
                        else -> 3
                    }
                    PodiumUserCard(
                        user = user,
                        rank = rank,
                        onClick = { onUserClick(rank, user) }
                    )
                } else {
                    Spacer(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}

@Composable
private fun PodiumUserCard(
    user: LeaderboardUser,
    rank: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val themeColor = when (rank) {
        1 -> Color(0xFF9A8600) // Gold
        2 -> Color(0xFFC0C0C0) // Silver
        else -> Color(0xFFCD7F32) // Bronze
    }

    val avatarSize = when (rank) {
        1 -> 80.dp
        else -> 64.dp
    }

    val crownSize = when (rank) {
        1 -> 28.dp
        else -> 20.dp
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        // Crown or medal icon
        Icon(
            imageVector = Icons.Default.EmojiEvents,
            contentDescription = "Rank Medal",
            tint = themeColor,
            modifier = Modifier.size(crownSize)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Avatar
        Box(
            modifier = Modifier
                .size(avatarSize)
                .clip(CircleShape)
                .border(3.dp, themeColor, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            val url = user.avatar_url
            if (url.isNullOrBlank()) {
                PodiumAvatarPlaceholder(username = user.username ?: "", size = avatarSize)
            } else {
                SubcomposeAsyncImage(
                    model = url,
                    contentDescription = user.username,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        PodiumAvatarPlaceholder(username = user.username ?: "", size = avatarSize)
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Name
        Text(
            text = user.username ?: "Unknown",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        // Count badge
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            color = themeColor.copy(alpha = 0.15f),
            contentColor = themeColor,
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Movie,
                    contentDescription = "Movies Count",
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = "${user.movies_count}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}

@Composable
private fun PodiumAvatarPlaceholder(
    username: String,
    size: androidx.compose.ui.unit.Dp,
    modifier: Modifier = Modifier
) {
    val initial = if (username.isNotBlank()) {
        username.take(1).uppercase()
    } else {
        "?"
    }

    Box(
        modifier = modifier
            .size(size)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = (size.value * 0.4f).sp,
            fontWeight = FontWeight.Bold
        )
    }
}
