package com.example.moovie.ui.screens

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moovie.presentation.explorer.MovieExplorerViewModel
import com.example.moovie.ui.components.CinemaDetailsCard
import com.example.moovie.platform.permissions.rememberMultiplePermissions
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import org.koin.androidx.compose.koinViewModel

/**
 * Premium Movie Explorer Screen.
 * Integrates Google Maps with location permission and coordinates tracking.
 */
@Composable
fun MovieExplorerScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: MovieExplorerViewModel = koinViewModel()
) {
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
