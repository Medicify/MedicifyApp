package com.medicify.app.ui.screen.camera

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.medicify.app.mlkit.ocr.TextRecognitionView
import com.medicify.app.ui.common.UiState
import com.medicify.app.ui.component.CircularLoading
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
    cameraViewModel: CameraViewModel = koinViewModel(),
    onBackPressed: () -> Unit,
    onDrugsFound: (String) -> Unit,
    isAuthenticated: Boolean,
) {
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )

    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TextRecognitionView(
            onTextFound = {
                val extractedText = it.split("\n").joinToString(" ")
                cameraViewModel.addExtractedText(extractedText)
            },
            onBackPressed = onBackPressed,
            isAuthenticated = isAuthenticated
        )

        cameraViewModel.listOfDrugItem.collectAsState(initial = UiState.Loading).value.let { drugsItem ->
            when (drugsItem) {
                is UiState.Loading -> {
                    CircularLoading(modifier)
                }

                is UiState.Success -> {
                    if (drugsItem.data.isNotEmpty()) {
                        onDrugsFound(drugsItem.data[0].id)
                        cameraViewModel.clearList()
                    }
                }

                is UiState.Error -> {}
            }
        }
    }
}

