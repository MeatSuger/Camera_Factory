package com.opencv.camerafactory.Camera

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.util.Log
import android.view.Surface
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.CameraController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun ComposeGLSurfaceView(
    cameraController: CameraController,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    AndroidView(
        modifier = modifier.fillMaxSize(),
        factory = { ctx ->
            CameraGLSurfaceView(ctx).apply {
                setSurfaceReadyCallback { surface ->

                    // Surface 准备好时执行相机绑定或其他逻辑
                    Log.d("Camera", "Surface is ready: $surface")
                    // 在此绑定 Camera 或者设置其他依赖 Surface 的操作
                    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                    cameraProviderFuture.addListener({
                        val cameraProvider = cameraProviderFuture.get()

                        // 创建预览用例
                        val preview = Preview.Builder()
                            .build()
                            .also {
                                it.setSurfaceProvider { request ->
                                    request.provideSurface(
                                        surface,
                                        context.mainExecutor
                                    ) { result ->
                                        // Surface 释放时处理逻辑（如果需要）
                                    }
                                }
                            }

                        // 选择相机
                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                        try {
                            // 绑定生命周期与用例
                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(
                                lifecycleOwner,
                                cameraSelector,
                                preview
                            )
                            this.post {
                                val rotation = this.display.rotation
                                cameraController.setZoomRatio(1.0f) // 镜像模式自动对齐
                                this.rotation = 90.0f
                            }
                        } catch (exc: Exception) {
                            Log.e("CameraX", "Use case binding failed", exc)
                        }
                    }, ContextCompat.getMainExecutor(context))}}}


//        factory = { context ->
//            val glSurfaceView = CameraGLSurfaceView(context)
//            val surface = glSurfaceView.getSurface()
//
//            // 使用 Camera2 配置会话
//            val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
//            val cameraId = cameraManager.cameraIdList.first { id ->
//                cameraManager.getCameraCharacteristics(id)
//                    .get(CameraCharacteristics.LENS_FACING) == CameraCharacteristics.LENS_FACING_BACK
//            }
//
//            if (ActivityCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.CAMERA
//                ) != PackageManager.PERMISSION_GRANTED
//            ) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                    val REQUEST_CAMERA_PERMISSION = 0
//                ActivityCompat.requestPermissions(
//                    context.applicationContext as Activity, // 当前 Activity
//                    arrayOf(Manifest.permission.CAMERA), // 请求的权限
//                    REQUEST_CAMERA_PERMISSION // 自定义请求码
//                )
//            }
//            cameraManager.openCamera(cameraId, object : CameraDevice.StateCallback() {
//                override fun onOpened(camera: CameraDevice) {
//                    camera.createCaptureSession(listOf(surface), object : CameraCaptureSession.StateCallback() {
//                        override fun onConfigured(session: CameraCaptureSession) {
//                            val captureRequest = camera.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW).apply {
//                                if (surface != null) {
//                                    addTarget(surface)
//                                }
//                            }
//                            session.setRepeatingRequest(captureRequest.build(), null, null)
//                        }
//
//                        override fun onConfigureFailed(session: CameraCaptureSession) {
//                            // 处理失败
//                        }
//                    }, null)
//                }
//
//                override fun onDisconnected(camera: CameraDevice) {
//                    camera.close()
//                }
//
//                override fun onError(camera: CameraDevice, error: Int) {
//                    camera.close()
//                }
//            }, null)
//
//            glSurfaceView
//        }
    )
}
