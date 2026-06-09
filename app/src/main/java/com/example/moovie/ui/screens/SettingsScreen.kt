package com.example.moovie.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moovie.R
import com.example.moovie.data.model.AppLanguage
import com.example.moovie.data.model.AppTheme
import com.example.moovie.presentation.settings.SettingsViewModel
import com.example.moovie.ui.components.MoovieButton
import com.example.moovie.ui.components.MoovieTextField
import org.koin.androidx.compose.koinViewModel

/**
 * ettings Screen.
 * Allows customizing app theme, language, and profile details.
 */
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel()
) {
    val currentTheme by viewModel.appTheme.collectAsState()
    val currentLanguage by viewModel.appLanguage.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val saveSuccessMsg = stringResource(id = R.string.profile_save_success)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // Theme Selection Card
        Text(
            text = stringResource(id = R.string.settings_theme_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

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
                    .padding(vertical = 8.dp)
            ) {
                SettingsOptionRow(
                    title = stringResource(id = R.string.settings_theme_system),
                    icon = Icons.Default.Android,
                    selected = currentTheme == AppTheme.SYSTEM,
                    onClick = { viewModel.setAppTheme(AppTheme.SYSTEM) }
                )
                
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                SettingsOptionRow(
                    title = stringResource(id = R.string.settings_theme_light),
                    icon = Icons.Default.LightMode,
                    selected = currentTheme == AppTheme.LIGHT,
                    onClick = { viewModel.setAppTheme(AppTheme.LIGHT) }
                )

                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                SettingsOptionRow(
                    title = stringResource(id = R.string.settings_theme_dark),
                    icon = Icons.Default.DarkMode,
                    selected = currentTheme == AppTheme.DARK,
                    onClick = { viewModel.setAppTheme(AppTheme.DARK) }
                )
            }
        }

        // Language Selection Card
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.settings_language_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

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
                    .padding(vertical = 8.dp)
            ) {
                SettingsOptionRow(
                    title = AppLanguage.ITALIAN.displayName,
                    leadingEmoji = AppLanguage.ITALIAN.flagEmoji,
                    selected = currentLanguage == AppLanguage.ITALIAN,
                    onClick = { viewModel.setAppLanguage(AppLanguage.ITALIAN) }
                )
                
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                SettingsOptionRow(
                    title = AppLanguage.ENGLISH.displayName,
                    leadingEmoji = AppLanguage.ENGLISH.flagEmoji,
                    selected = currentLanguage == AppLanguage.ENGLISH,
                    onClick = { viewModel.setAppLanguage(AppLanguage.ENGLISH) }
                )
            }
        }

        // Security Selection Card
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.settings_security_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            val biometricEnabled by viewModel.biometricLockEnabled.collectAsState()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.settings_biometric_lock_toggle),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = stringResource(id = R.string.settings_biometric_lock_desc),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = biometricEnabled,
                    onCheckedChange = { viewModel.setBiometricLockEnabled(it) },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                        uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                        uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                )
            }
        }

        // Profile Edit Card
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.title_profile),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

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
    }
}

@Composable
private fun SettingsOptionRow(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    icon: ImageVector? = null,
    leadingEmoji: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                onClick = onClick,
                role = Role.RadioButton
            )
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (leadingEmoji != null) {
                Text(
                    text = leadingEmoji,
                    fontSize = 22.sp,
                    modifier = Modifier.size(24.dp),
                    textAlign = TextAlign.Center
                )
            } else if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
            )
        }
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )
        )
    }
}
