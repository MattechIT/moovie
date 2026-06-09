package com.example.moovie.ui.screens

import android.content.Context
import android.content.ContextWrapper
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.example.moovie.platform.biometric.BiometricService
import org.koin.compose.koinInject
import com.example.moovie.R
import com.example.moovie.ui.components.MoovieButton
import com.example.moovie.ui.components.MoovieNotificationBanner

/**
 * Biometric Unlock Screen.
 * Displays when biometric lock is enabled at startup.
 * Prevents user bypass by intercepting Back presses to exit the app.
 */
@Composable
fun BiometricUnlockScreen(
    onUnlockSuccess: () -> Unit,
    biometricService: BiometricService = koinInject()
) {
    val context = LocalContext.current
    val localActivity = com.example.moovie.LocalActivity.current
    val activity = remember(context, localActivity) { localActivity ?: context.findActivity() }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Intercept back presses to exit the app, preventing security bypass
    BackHandler {
        activity?.finish()
    }

    // Function to launch the native Biometric Prompt
    val triggerBiometricPrompt = {
        if (activity != null) {
            biometricService.authenticate(
                activity = activity,
                title = context.getString(R.string.biometric_prompt_title),
                subtitle = context.getString(R.string.biometric_prompt_subtitle),
                onSuccess = {
                    errorMessage = null
                    onUnlockSuccess()
                },
                onError = { err ->
                    errorMessage = err
                },
                onFailed = {
                    errorMessage = context.getString(R.string.auth_unknown_error)
                }
            )
        } else {
            errorMessage = context.getString(R.string.auth_biometric_unavailable)
        }
    }

    // Trigger biometric prompt automatically on screen entry
    LaunchedEffect(Unit) {
        triggerBiometricPrompt()
    }

    // UI design with subtle dark gradient or theme compliance
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Glowing Lock Icon container
            Box(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )
            }

            // Lock Titles
            Text(
                text = stringResource(id = R.string.biometric_lock_screen_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Text(
                text = stringResource(id = R.string.biometric_lock_screen_desc),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )


            Spacer(modifier = Modifier.height(24.dp))

            // Unlock Button to re-trigger prompt manually
            MoovieButton(
                text = stringResource(id = R.string.biometric_lock_screen_unlock_btn),
                onClick = {
                    triggerBiometricPrompt()
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        }

        // Floating notification banner for security errors
        MoovieNotificationBanner(
            visible = errorMessage != null,
            message = errorMessage ?: "",
            onDismiss = { errorMessage = null },
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

/**
 * Extension helper function to retrieve the FragmentActivity from local context.
 */
private fun Context.findActivity(): FragmentActivity? {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is FragmentActivity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    return null
}
