package com.medicify.app.mlkit.ocr

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.medicify.app.R
import com.medicify.app.ui.utils.getVectorResource
import java.util.concurrent.Executors

@Composable
fun TextRecognitionView(
    context: Context,
    lifecycleOwner: LifecycleOwner,
    onTextFound: (String) -> Unit,
    onBackPressed: () -> Unit,
    isCameraActive: Boolean
) {
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    var preview by remember {
        mutableStateOf<Preview?>(null)
    }
    val executor = ContextCompat.getMainExecutor(context)
    val cameraProvider = cameraProviderFuture.get()
    val cameraExecutor = remember { Executors.newSingleThreadExecutor() }

    Box {
        AndroidView(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.7f),
            factory = { androidViewContext ->
                val previewView = PreviewView(androidViewContext)
                cameraProviderFuture.addListener({


                    val imageAnalyzer by lazy {
                        ImageAnalysis.Builder()
                            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                            .build()
                            .apply {
                                setAnalyzer(
                                    cameraExecutor,
                                    ImageAnalyzer(onTextFound = onTextFound)
                                )
                            }

                    }

                    val cameraSelector = CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                        .build()

                    cameraProvider.unbindAll()

                    // it suppose to stop the camera when a text is detected
                    if (isCameraActive) {
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            imageAnalyzer,
                            preview
                        )
                    } else {
                        cameraProvider.unbindAll()
                    }


                }, executor)

                preview = Preview.Builder().build().apply {
                    setSurfaceProvider(previewView.surfaceProvider)
                }
                previewView
            }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .align(Alignment.TopStart)
        ) {
            IconButton(
                onClick = onBackPressed
            ) {
                Icon(
                    imageVector = R.drawable.circle_arrow_left_24px.getVectorResource(),
                    contentDescription = "back",
                    tint = Color.White
                )
            }
        }
    }

}