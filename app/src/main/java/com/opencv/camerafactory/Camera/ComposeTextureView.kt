package com.opencv.camerafactory.Camera

import android.graphics.Bitmap
import android.graphics.SurfaceTexture
import android.util.Log
import android.view.TextureView
import android.view.TextureView.SurfaceTextureListener
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


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
                                val rotation =  2
                                drawImageProxy(imageProxy, this@apply,rotation)
                                imageProxy.close()
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

                    Log.d("TAG", "ComposeTextureView: $isHardwareAccelerated")
                }
            },
        )
    }
}

fun drawImageProxy(imageProxy: ImageProxy?, holder: TextureView, rotation: Int?) {
    imageProxy?.let { proxy ->
        val canvas = holder.lockCanvas()
        proxy.use { mProxy ->
            bitmap =
                if (bitmap == null || bitmap!!.width != mProxy.width || bitmap!!.height != mProxy.height) {
                    Bitmap.createBitmap(mProxy.width, mProxy.height, Bitmap.Config.ARGB_8888)
                } else {
                    bitmap
                }

            bitmap = mProxy.toBitmap()
            Log.d(
                "drawImageProxy",
                "Bitmap created with size: ${bitmap!!.width}x${bitmap!!.height}"
            )
            matrix.reset()
            val scaleFactor = 0.75f
            matrix.preScale(scaleFactor, scaleFactor)

            val cx = bitmap!!.width / 2f
            val cy = bitmap!!.height / 2f
            matrix.preTranslate(-cx, -cy)
            if (rotation != null) {
                matrix.postRotate(-rotation*90f)
            }

            val rotatedWidth = bitmap!!.height * scaleFactor
            val rotatedHeight = bitmap!!.width * scaleFactor
            matrix.postTranslate(rotatedWidth / 2f, rotatedHeight / 2f)

            holder.post {
                if (canvas != null) {
                    canvas.drawBitmap(bitmap!!, matrix, null)
                    holder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }
}
