package com.example.moovie.ui.screens

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.moovie.R
import com.example.moovie.presentation.profile.ProfileViewModel
import com.example.moovie.ui.components.MetricCard
import com.example.moovie.ui.components.MoovieButton
import com.example.moovie.ui.components.MoovieTextField
import com.example.moovie.ui.components.NavigationRow
import org.koin.androidx.compose.koinViewModel
import java.io.File

/**User Profile Screen.
 * Allows avatar customization, name and bio updates,
 * displays local movie metrics, and provides quick navigation shortcuts.
 */
@Composable
fun ProfileScreen(
    onNavigateToFavorites: () -> Unit,
    onNavigateToWatchlist: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToStats: () -> Unit,
    onNavigateToMovieExplorer: () -> Unit,
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    val favoriteCount by viewModel.favoriteCount.collectAsState()
    val watchlistCount by viewModel.watchlistCount.collectAsState()

    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    // Gallery media picker
    val pickMediaLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            viewModel.updateAvatarUri(uri.toString())
        }
    }

    // Take picture
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            tempCameraUri?.let { uri ->
                viewModel.updateAvatarUri(uri.toString())
            }
        }
    }

    val saveSuccessMsg = stringResource(id = R.string.profile_save_success)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Circular Avatar Display
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (viewModel.avatarUriInput.isBlank()) {
                    AvatarPlaceholder(username = viewModel.usernameInput)
                } else {
                    SubcomposeAsyncImage(
                        model = viewModel.avatarUriInput,
                        contentDescription = stringResource(id = R.string.profile_avatar_desc),
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    ) {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            AvatarPlaceholder(username = viewModel.usernameInput)
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        try {
                            val uri = createTempImageUri(context)
                            tempCameraUri = uri
                            takePictureLauncher.launch(uri)
                        } catch (e: Exception) {
                            Toast.makeText(context, "Errore fotocamera: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = stringResource(id = R.string.profile_change_avatar_camera),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(id = R.string.profile_change_avatar_camera),
                        fontSize = 12.sp
                    )
                }

                Button(
                    onClick = {
                        pickMediaLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoLibrary,
                        contentDescription = stringResource(id = R.string.profile_change_avatar_gallery),
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = stringResource(id = R.string.profile_change_avatar_gallery),
                        fontSize = 12.sp
                    )
                }

                if (viewModel.avatarUriInput.isNotBlank()) {
                    IconButton(
                        onClick = {
                            viewModel.updateAvatarUri("")
                        },
                        modifier = Modifier
                            .size(12.dp)
                            .background(MaterialTheme.colorScheme.error.copy(alpha = 0.25f), CircleShape)
                            .border(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.8f), CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Rimuovi foto",
                            tint = MaterialTheme.colorScheme.error,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }

        // Realtime Movie Metrics
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MetricCard(
                count = favoriteCount,
                label = stringResource(id = R.string.profile_metrics_favorites),
                icon = Icons.Default.Favorite,
                onClick = onNavigateToFavorites,
                modifier = Modifier.weight(1f)
            )

            MetricCard(
                count = watchlistCount,
                label = stringResource(id = R.string.profile_metrics_watchlist),
                icon = Icons.AutoMirrored.Filled.List,
                onClick = onNavigateToWatchlist,
                modifier = Modifier.weight(1f)
            )
        }

        // Editable Info
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MoovieTextField(
                    value = viewModel.usernameInput,
                    onValueChange = viewModel::updateUsername,
                    label = stringResource(id = R.string.profile_username_label),
                    modifier = Modifier.fillMaxWidth()
                )

                MoovieTextField(
                    value = viewModel.bioInput,
                    onValueChange = viewModel::updateBio,
                    label = stringResource(id = R.string.profile_bio_label),
                    modifier = Modifier.fillMaxWidth()
                )
                MoovieButton(
                    text = stringResource(id = R.string.profile_edit_save),
                    onClick = {
                        viewModel.saveProfile(
                            onSuccess = {
                                Toast.makeText(context, saveSuccessMsg, Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Quick Settings & Stats Navigation Shortcuts
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HorizontalDivider(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                thickness = 1.dp
            )

            NavigationRow(
                title = stringResource(id = R.string.title_settings),
                icon = Icons.Default.Settings,
                onClick = onNavigateToSettings
            )
            NavigationRow(
                title = stringResource(id = R.string.title_stats),
                icon = Icons.AutoMirrored.Filled.ShowChart,
                onClick = onNavigateToStats
            )
            NavigationRow(
                title = stringResource(id = R.string.title_movie_explorer),
                icon = Icons.Default.Explore,
                onClick = onNavigateToMovieExplorer
            )
            OutlinedButton(
                onClick = {
                    viewModel.logout(
                        onSuccess = onLogout
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.error
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f))
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = stringResource(id = R.string.profile_logout_btn),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.profile_logout_btn),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
private fun AvatarPlaceholder(
    username: String,
    modifier: Modifier = Modifier
) {
    val initial = if (username.isNotBlank()) {
        username.take(1).uppercase()
    } else {
        "U"
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = androidx.compose.ui.graphics.Brush.linearGradient(
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
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold
        )
    }
}



/**
 * Safe helper to allocate a temporary image file and return its FileProvider Uri.
 */
private fun createTempImageUri(context: Context): Uri {
    val tempFile = File.createTempFile(
        "avatar_capture_",
        ".jpg",
        context.externalCacheDir
    )
    return FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        tempFile
    )
}
