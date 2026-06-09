package com.example.moovie.ui.screens

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.moovie.R
import com.example.moovie.presentation.explorer.MovieExplorerViewModel
import com.example.moovie.ui.components.CinemaDetailsCard
import com.example.moovie.platform.permissions.rememberMultiplePermissions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import org.koin.androidx.compose.koinViewModel
import com.example.moovie.util.startActivitySafe

/**
 * Movie Explorer Screen.
 * Integrates Google Maps with location permission and coordinates tracking.
 */
@Composable
fun MovieExplorerScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: MovieExplorerViewModel = koinViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    // Permission handler using generic platform wrapper
    val permissionHandler = rememberMultiplePermissions(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    ) { results ->
        val granted = results.values.any { it.isGranted }
        viewModel.setPermissionGranted(granted)
    }

    // Proactive request at entry
    LaunchedEffect(Unit) {
        val initiallyGranted = permissionHandler.statuses.values.any { it.isGranted }
        viewModel.setPermissionGranted(initiallyGranted)
        if (!initiallyGranted) {
            permissionHandler.launchPermissionRequest()
        }
    }

    // Lifecycle observer to trigger GPS check when user returns from settings
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                val fineGranted = androidx.core.content.ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED
                val coarseGranted = androidx.core.content.ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == android.content.pm.PackageManager.PERMISSION_GRANTED
                val currentlyGranted = fineGranted || coarseGranted
                
                viewModel.setPermissionGranted(currentlyGranted)
                if (currentlyGranted) {
                    viewModel.fetchUserLocation()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Central Romagna default coordinates
    val defaultCoords = LatLng(44.2300, 12.2100)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultCoords, 9f)
    }

    // Auto-center camera on actual user location once resolved
    LaunchedEffect(uiState.userLocation) {
        uiState.userLocation?.let { coords ->
            cameraPositionState.position = CameraPosition.fromLatLngZoom(
                LatLng(coords.latitude, coords.longitude),
                11f
            )
        }
    }

    val mapProperties by remember(uiState.userLocationPermissionGranted) {
        mutableStateOf(
            MapProperties(isMyLocationEnabled = uiState.userLocationPermissionGranted)
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = MapUiSettings(myLocationButtonEnabled = uiState.userLocationPermissionGranted),
            onMapClick = {
                viewModel.selectCinema(null)
            }
        ) {
            uiState.cinemas.forEach { cinema ->
                val cinemaCoords = LatLng(cinema.latitude, cinema.longitude)
                Marker(
                    state = MarkerState(position = cinemaCoords),
                    title = cinema.name,
                    snippet = cinema.address,
                    onClick = {
                        viewModel.selectCinema(cinema)
                        false
                    }
                )
            }
        }

        // GPS Disabled Warning Banner
        AnimatedVisibility(
            visible = uiState.userLocationPermissionGranted && uiState.userLocation == null && !uiState.isLoading && !uiState.isGpsWarningDismissed,
            enter = slideInVertically(initialOffsetY = { -it }),
            exit = slideOutVertically(targetOffsetY = { -it }),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.explorer_gps_disabled),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = {
                             val intent = android.content.Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                             context.startActivitySafe(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.explorer_gps_enable_btn),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onError
                        )
                    }
                    IconButton(
                        onClick = { viewModel.dismissGpsWarning() },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(id = R.string.explorer_close_btn),
                            tint = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // Selected Cinema Details
        AnimatedVisibility(
            visible = uiState.selectedCinema != null,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it }),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
        ) {
            uiState.selectedCinema?.let { cinema ->
                CinemaDetailsCard(
                    cinema = cinema,
                    movies = uiState.selectedCinemaMovies,
                    onMovieClick = onMovieClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}
