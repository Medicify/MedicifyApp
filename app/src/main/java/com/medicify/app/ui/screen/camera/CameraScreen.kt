package com.medicify.app.ui.screen.camera

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.medicify.app.mlkit.ocr.TextRecognitionView
import com.medicify.app.ui.utils.copyToClipboard


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraScreen(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var extractedText by remember { mutableStateOf("") }
    var isCameraActive by remember {
        mutableStateOf(true)
    }
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )
    LaunchedEffect(Unit){
        cameraPermissionState.launchPermissionRequest()
    }


//    if (!cameraPermissionState.status.isGranted) {
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
//                // If the user has denied the permission but the rationale can be shown,
//                // then gently explain why the app requires this permission
//                "Izin Kamera dibutuhkan untuk mendeteksi Obat"
//            } else {
//                // If it's the first time the user lands on this feature, or the user
//                // doesn't want to be asked again for this permission, explain that the
//                // permission is required
//                "Untuk menggunakan fitur ini dibutuhkan izin Kamera"
//            }
//            Text(textToShow, style = MaterialTheme.typography.headlineLarge)
//            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
//                Text("Request izin kamera")
//            }
//        }
//    } else {
//    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TextRecognitionView(
            context = context,
            lifecycleOwner = lifecycleOwner,
            onTextFound = {
                extractedText = it
                if (it.trim().equals("Aqua", ignoreCase = true)) {
                    isCameraActive = !isCameraActive
                }
            },
            onBackPressed = {

            },
            isCameraActive = isCameraActive
        )
        Button(onClick = { copyToClipboard(context, extractedText) }) {

        }
        Text(
            text = extractedText,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp)
        )
    }

}

