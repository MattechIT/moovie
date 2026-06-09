package com.example.moovie.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moovie.R
import com.example.moovie.data.model.LeaderboardUser
import com.example.moovie.presentation.leaderboard.LeaderboardUiState
import com.example.moovie.presentation.leaderboard.LeaderboardViewModel
import com.example.moovie.ui.components.LeaderboardRow
import com.example.moovie.ui.components.PodiumSection
import com.example.moovie.ui.components.UserDetailDialog
import org.koin.androidx.compose.koinViewModel

@Composable
fun LeaderboardScreen(
    modifier: Modifier = Modifier,
    viewModel: LeaderboardViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var selectedUserForDialog by remember { mutableStateOf<Pair<Int, LeaderboardUser>?>(null) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Main Content Area
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is LeaderboardUiState.Loading -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
                is LeaderboardUiState.Error -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.leaderboard_error_failed),
                            color = MaterialTheme.colorScheme.error,
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                        Button(
                            onClick = { viewModel.loadLeaderboard() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(text = stringResource(id = R.string.home_retry))
                        }
                    }
                }
                is LeaderboardUiState.Success -> {
                    if (state.users.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.leaderboard_empty_list),
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                            textAlign = TextAlign.Center,
                            fontSize = 16.sp
                        )
                    } else {
                        LeaderboardContent(
                            users = state.users,
                            onUserClick = { rank, user ->
                                selectedUserForDialog = Pair(rank, user)
                            }
                        )
                    }
                }
            }
        }

        // Floating Action Button aligned manually to avoid double Scaffold WindowInsets spacing
        FloatingActionButton(
            onClick = { viewModel.loadLeaderboard() },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Refresh"
            )
        }

        // Display user details popup if selected
        selectedUserForDialog?.let { (rank, user) ->
            UserDetailDialog(
                rank = rank,
                user = user,
                onDismissRequest = { selectedUserForDialog = null }
            )
        }
    }
}

@Composable
private fun LeaderboardContent(
    users: List<LeaderboardUser>,
    onUserClick: (Int, LeaderboardUser) -> Unit,
    modifier: Modifier = Modifier
) {
    val topThree = users.take(3)
    val remainingUsers = users.drop(3)

    Column(modifier = modifier.fillMaxSize()) {
        // Top 3 Podium component
        PodiumSection(
            topThree = topThree,
            onUserClick = onUserClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Ranks other elements
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            itemsIndexed(remainingUsers) { index, user ->
                val rank = index + 4
                LeaderboardRow(
                    rank = rank,
                    user = user,
                    onClick = { onUserClick(rank, user) }
                )
            }
        }
    }
}
