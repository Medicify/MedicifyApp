package com.medicify.app.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.medicify.app.ui.screen.home.HomeScreen
import com.medicify.app.ui.theme.MedicifyTheme

@Composable
fun MedicifyApp(
    modifier: Modifier = Modifier
) {
    HomeScreen()
}

@Preview
@Composable
fun MedicifyAppPreview() {
    MedicifyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MedicifyApp()
        }
    }
}