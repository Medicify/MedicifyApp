package com.medicify.app.ui

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.medicify.app.ui.screen.home.HomeScreen
import com.medicify.app.ui.theme.MedicifyTheme

@Composable
fun MedicifyApp() {
    val context = LocalContext.current
    HomeScreen(navigateToDetail = {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    })
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