package com.opencv.camerafactory.Camera

import android.view.SurfaceView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun VulkanRenderView() {
    val context = LocalContext.current

    AndroidView(
        factory = {
            SurfaceView(context).apply {
                // 初始化 Vulkan 渲染
                initializeVulkan()
            }
        },
        modifier = Modifier.fillMaxSize()
    )
}

fun initializeVulkan() {
    TODO("Not yet implemented")
}
