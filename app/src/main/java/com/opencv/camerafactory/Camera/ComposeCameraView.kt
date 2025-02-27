package com.opencv.camerafactory.Camera

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun ComposeCameraView(
    cameraController: LifecycleCameraController, modifier: Modifier
) {

    // 使用Scaffold构建布局
    Scaffold(modifier = modifier) { innerPadding: PaddingValues ->
        // 使用AndroidView构建自定义视图
        AndroidView(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            factory = { context ->
                // 创建PreviewView
                PreviewView(context).apply {
                    // 设置背景颜色
                    setBackgroundColor(Color.Transparent.toArgb())
                    // 设置布局参数
                    layoutParams = LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    // 设置缩放类型
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                    // 设置实现模式
                    implementationMode = PreviewView.ImplementationMode.PERFORMANCE

                }.also { previewView ->
                    // 将cameraController绑定到previewView
                    previewView.controller = cameraController

                }
            },
            // 释放资源
            onRelease = {
                cameraController.unbind()
            },
        )

    }
}