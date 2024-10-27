package com.opencv.camerafactory

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PixelFormat
import android.graphics.SurfaceTexture
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.TextureView
import android.view.TextureView.SurfaceTextureListener
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


private val matrix = Matrix() // 复用Matrix对象
private var bitmap: Bitmap? = null // 复用Bitmap对象

@Composable
fun ComposeTextureView(
    cameraController: CameraController,
    modifier: Modifier
) {
    Scaffold(modifier = modifier) { innerPadding: PaddingValues ->

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),

            factory = { context ->
                TextureView(context).apply {
                    surfaceTextureListener = object : SurfaceTextureListener {
                        override fun onSurfaceTextureAvailable(
                            surface: SurfaceTexture,
                            width: Int,
                            height: Int
                        ) {
                            cameraController.setImageAnalysisAnalyzer(context.mainExecutor) { imageProxy ->
                                drawImageProxy(imageProxy, this@apply)
                            }

                        }

                        override fun onSurfaceTextureSizeChanged(
                            surface: SurfaceTexture,
                            width: Int,
                            height: Int
                        ) {
//                                TODO("Not yet implemented")
                        }

                        override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
//                                TODO("Not yet implemented")
                            return true
                        }

                        override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
//                                TODO("Not yet implemented")
                        }
                    }
                    isOpaque = false

                    scaleX
                    Log.d("TAG", "ComposeTextureView: $isHardwareAccelerated")
                }
            },
        )
    }
}

fun drawImageProxy(imageProxy: ImageProxy?, holder: TextureView) {
    val startTime = System.currentTimeMillis()

    imageProxy?.let { proxy ->
        val canvas = holder.lockCanvas()
        try {
            bitmap =
                if (bitmap == null || bitmap!!.width != proxy.width || bitmap!!.height != proxy.height) {
                    Bitmap.createBitmap(proxy.width, proxy.height, Bitmap.Config.ARGB_8888)
                } else {
                    bitmap
                }

            bitmap = proxy.toBitmap()


            matrix.reset()
            matrix.preScale(0.75f,0.75f)
            // 计算Bitmap的中心点
            val cx = bitmap!!.width / 2.0F
            val cy = bitmap!!.height / 2.0F

            // 将Matrix的旋转中心移动到Bitmap的中心
            matrix.preTranslate(-cx, -cy)

            // 设置旋转角度，例如90度
            matrix.postRotate(90.0F)


            // 将Matrix平移回来，同时考虑旋转后的位置
            val rotatedWidth = bitmap!!.height.toFloat()
            val rotatedHeight = bitmap!!.width.toFloat()
            matrix.postTranslate(rotatedWidth / 2.0F, rotatedHeight / 2.0F)


            holder.post {
                if (canvas != null) {
                    canvas.drawBitmap(bitmap!!, matrix, null)
                    holder.unlockCanvasAndPost(canvas)
                }
            }

        } finally {
            proxy.close()
        }
    }
    val endTime = System.currentTimeMillis()
    val executionTime = endTime - startTime

    // 输出执行时间到日志
    Log.d("drawImageProxy", "Execution time: $executionTime ms")
}