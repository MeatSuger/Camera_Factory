package com.opencv.camerafactory.Camera

import android.app.Application
import android.util.Range
import android.util.Size
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.view.LifecycleCameraController
import androidx.lifecycle.AndroidViewModel

class CameraViewModel(application: Application) : AndroidViewModel(application) {
    // 缓存的 CameraController
    val cameraController = LifecycleCameraController(application).apply {
        cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
        imageAnalysisOutputImageFormat = ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888
        imageAnalysisResolutionSelector = ResolutionSelector.Builder()
            .setResolutionStrategy(
                ResolutionStrategy(
                    Size(720, 720),
                    ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER
                )
            )
            .build()
        videoCaptureTargetFrameRate = Range(15, 30)
    }
}
