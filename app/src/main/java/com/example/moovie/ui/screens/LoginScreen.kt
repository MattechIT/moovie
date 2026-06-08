package com.example.moovie.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moovie.R
import com.example.moovie.presentation.auth.AuthUiState
import com.example.moovie.ui.components.MoovieButton
import com.example.moovie.ui.components.MoovieTextField
import com.example.moovie.ui.components.MoovieNotificationBanner
import androidx.compose.runtime.*

/**
 * Stateless Login screen design.
 * Accepts UI state and emits interaction events back to the parent container.
 */
@Composable
fun LoginScreen(
    email: String,
    password: String,
    uiState: AuthUiState,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClick: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var showErrorBanner by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    LaunchedEffect(uiState) {
        if (uiState is AuthUiState.Error) {
            errorMessage = uiState.message
            showErrorBanner = true
        } else {
            showErrorBanner = false
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(id = R.string.auth_login_header),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(24.dp))

            MoovieTextField(
                value = email,
                onValueChange = onEmailChanged,
                label = stringResource(id = R.string.auth_email_label),
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                enabled = uiState !is AuthUiState.Loading
            )
            
            Spacer(modifier = Modifier.height(12.dp))

            MoovieTextField(
                value = password,
                onValueChange = onPasswordChanged,
                label = stringResource(id = R.string.auth_password_label),
                modifier = Modifier.fillMaxWidth(),
                isPassword = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                enabled = uiState !is AuthUiState.Loading
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // We now display errors in the bottom slide-in banner, 
            // so we can omit the static inline error text to keep the UI clean.

            MoovieButton(
                text = stringResource(id = R.string.auth_login_button),
                onClick = onLoginClick,
                isLoading = uiState is AuthUiState.Loading,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Toggle back to registration
            TextButton(
                onClick = onNavigateToRegister,
                enabled = uiState !is AuthUiState.Loading
            ) {
                Text(
                    text = stringResource(id = R.string.auth_to_register_link),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 14.sp
                )
            }
        }

        // Error Banner
        MoovieNotificationBanner(
            visible = showErrorBanner,
            message = errorMessage,
            onDismiss = { showErrorBanner = false },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

