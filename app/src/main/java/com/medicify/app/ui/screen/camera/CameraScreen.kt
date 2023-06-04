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
import androidx.compose.runtime.collectAsState
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
import com.medicify.app.ui.common.UiState
import com.medicify.app.ui.component.CircularLoading
import com.medicify.app.ui.utils.copyToClipboard
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
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var extractedText by remember { mutableStateOf("") }
    val isCameraActive by remember {
        mutableStateOf(true)
    }
    val cameraPermissionState = rememberPermissionState(
        Manifest.permission.CAMERA
    )
    LaunchedEffect(Unit) {
        cameraPermissionState.launchPermissionRequest()
    }

//    val title by cameraViewModel.title.collectAsState(initial = UiState.Loading)


    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TextRecognitionView(
            context = context,
            lifecycleOwner = lifecycleOwner,
            onTextFound = {
                extractedText = it
            },
            onBackPressed = onBackPressed,
            isCameraActive = isCameraActive,
            isAuthenticated = isAuthenticated
        )
        Button(onClick = { copyToClipboard(context, extractedText) }) {

        }
        cameraViewModel.getTitle(extractedText.split("\n").joinToString(" "))
            .collectAsState(initial = UiState.Loading).value.let { drugsItem ->
                when (drugsItem) {
                    is UiState.Loading -> {
                        CircularLoading(modifier)
                    }

                    is UiState.Success -> {
                        val title = if (drugsItem.data.isNotEmpty()) drugsItem.data[0].title else ""
                        onDrugsFound(title)
                        Text(
                            text = title,
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White)
                                .padding(16.dp)
                        )
                    }

                    is UiState.Error -> {}
                }
            }
    }


//    LaunchedEffect(key1 = true) {
//    }


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

}

