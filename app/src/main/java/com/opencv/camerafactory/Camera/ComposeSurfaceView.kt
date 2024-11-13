package com.opencv.camerafactory.Camera

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.PixelFormat
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

 val matrix = Matrix() // 复用Matrix对象
 var bitmap: Bitmap? = null // 复用Bitmap对象

@Composable
fun ComposeSurfaceView(
    cameraController: CameraController, modifier: Modifier
) {
    Scaffold(modifier = modifier) { innerPadding: PaddingValues ->

        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),

            factory = { context ->
                SurfaceView(context).apply {
                    holder.addCallback(object : SurfaceHolder.Callback {
                        override fun surfaceCreated(holder: SurfaceHolder) {
                            cameraController.setImageAnalysisAnalyzer(context.mainExecutor) {
                                drawImageProxy(it, holder)
                            }
                        }

                        override fun surfaceChanged(
                            holder: SurfaceHolder, format: Int, width: Int, height: Int
                        ) {
                            //                        TODO("Not yet implemented surface")
                        }

                        override fun surfaceDestroyed(holder: SurfaceHolder) {
                            //                        TODO("Not yet implemented")
                        }

                    })
                    holder.setFormat(PixelFormat.TRANSLUCENT)
                    setZOrderOnTop(true)
                }
            },
        )
    }
}

fun drawImageProxy(imageProxy: ImageProxy?, holder: SurfaceHolder) {
    val startTime = System.currentTimeMillis()

    imageProxy?.let { proxy ->
        val canvas = holder.lockHardwareCanvas()
        try {

            bitmap =
                if (bitmap == null || bitmap!!.width != proxy.width || bitmap!!.height != proxy.height) {
                    Bitmap.createBitmap(proxy.width, proxy.height, Bitmap.Config.ARGB_8888)
                } else {
                    bitmap
                }

            bitmap = proxy.toBitmap()

            matrix.reset()

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

            // 镜像变换（水平镜像）
//            matrix.postScale(-1.0F, -1.0F, bitmap.width / 2.0F, bitmap.height / 2.0F)

            canvas.drawBitmap(bitmap!!, matrix, null)

        } finally {
            holder.unlockCanvasAndPost(canvas)
            proxy.close()
        }
    }
    val endTime = System.currentTimeMillis()
    val executionTime = endTime - startTime

    // 输出执行时间到日志
//    Log.d("drawImageProxy", "Execution time: $executionTime ms")
}