package com.opencv.camerafactory.Camera

import android.content.Intent
import android.util.Log
import android.util.Range
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalLensFacing
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.opencv.camerafactory.FireBase.MyFirebaseMessagingService
import com.opencv.camerafactory.PreviewCard
import com.opencv.camerafactory.Util.copyTokenToClipboard
import com.opencv.camerafactory.ui.theme.CameraFactoryTheme


@androidx.annotation.OptIn(ExperimentalLensFacing::class)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ControlCamera() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val screenWidth = LocalConfiguration.current.screenWidthDp

    // 初始化摄像头控制器，避免重新组合时重复初始化
    val cameraController = remember {
        LifecycleCameraController(context).apply {
            cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
            imageAnalysisOutputImageFormat = ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888
            imageAnalysisResolutionSelector = ResolutionSelector.Builder()
                .setResolutionStrategy(
                    ResolutionStrategy(
                        android.util.Size(720, 720),
                        ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER
                    )
                )
                .build()
            videoCaptureTargetFrameRate = Range(30, 30)
            bindToLifecycle(lifecycleOwner)
        }
    }

    CameraFactoryTheme {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.size(screenWidth.dp)
        ) {
            Column {
                // 相机预览
                Row {
                    PreviewCard(
                        cameraController = cameraController,
                        modifier = Modifier.size(screenWidth.dp / 2)
                    )
                }

                // 后置摄像头按钮
                Button(
                    onClick = {
                        cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    },
                    modifier = Modifier
                        .size(height = 50.dp, width = screenWidth.dp)
                        .padding(4.dp)
                ) {
                    Text(text = "后置摄像头", style = MaterialTheme.typography.bodyMedium)
                }

                // 前置摄像头按钮
                Button(
                    onClick = {
                        cameraController.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                    },
                    modifier = Modifier
                        .size(height = 50.dp, width = screenWidth.dp)
                        .padding(4.dp)
                ) {
                    Text(text = "前置摄像头", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

