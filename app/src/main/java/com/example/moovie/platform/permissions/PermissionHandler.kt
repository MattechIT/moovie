package com.example.moovie.platform.permissions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

/**
 * Status of a specific permission request.
 */
enum class PermissionStatus {
    Unknown, Granted, Denied, PermanentlyDenied;

    val isGranted get() = this == Granted
    val isDenied get() = this == Denied || this == PermanentlyDenied
}

/**
 * Interface to trigger permission request and observe results.
 */
interface MultiplePermissionHandler {
    val statuses: Map<String, PermissionStatus>
    fun launchPermissionRequest()
}

/**
 * Composable helper to manage multiple permissions reactively at runtime.
 */
@Composable
fun rememberMultiplePermissions(
    permissions: List<String>,
    onResult: (status: Map<String, PermissionStatus>) -> Unit
): MultiplePermissionHandler {
    val context = LocalContext.current
    val activity = context as? Activity

    var statuses by remember {
        mutableStateOf(
            permissions.associateWith { permission ->
                if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED)
                    PermissionStatus.Granted
                else PermissionStatus.Unknown
            }
        )
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        statuses = result.mapValues { (permission, isGranted) ->
            when {
                isGranted -> PermissionStatus.Granted
                activity?.shouldShowRequestPermissionRationale(permission) == true -> PermissionStatus.Denied
                else -> PermissionStatus.PermanentlyDenied
            }
        }
        onResult(statuses)
    }

    return remember(permissionLauncher, statuses) {
        object : MultiplePermissionHandler {
            override val statuses get() = statuses
            override fun launchPermissionRequest() {
                permissionLauncher.launch(permissions.toTypedArray())
            }
        }
    }
}
