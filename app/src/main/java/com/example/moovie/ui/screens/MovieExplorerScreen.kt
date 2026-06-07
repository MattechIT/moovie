package com.example.moovie.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.moovie.presentation.explorer.MovieExplorerViewModel
import com.example.moovie.ui.components.CinemaDetailsCard
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import org.koin.androidx.compose.koinViewModel

/**
 * Premium Movie Explorer Screen.
 * Integrates Google Maps with location permission
 */
@Composable
fun MovieExplorerScreen(
    onMovieClick: (Int) -> Unit,
    viewModel: MovieExplorerViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Launcher for geolocations
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false
        viewModel.setPermissionGranted(fineGranted || coarseGranted)
    }

    // Proactive request at entry
    LaunchedEffect(Unit) {
        val fineGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val coarseGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        
        viewModel.setPermissionGranted(fineGranted || coarseGranted)
        if (!fineGranted && !coarseGranted) {
            permissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    // Central Rome coords default (Romagna)
    val defaultCoords = LatLng(44.2300, 12.2100)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultCoords, 9f)
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
