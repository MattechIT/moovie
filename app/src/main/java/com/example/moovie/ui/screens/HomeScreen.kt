package com.example.moovie.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(onNavigateToDetail: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .clickable { onNavigateToDetail(1) }, // Naviga al dettaglio del film con ID 1
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Home Feed (Clicca per Dettaglio Film)",
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
