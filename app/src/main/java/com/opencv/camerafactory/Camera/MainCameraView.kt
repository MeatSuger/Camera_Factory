package com.opencv.camerafactory.Camera


import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.opencv.camerafactory.R
import com.opencv.camerafactory.Util.CameraType
import com.opencv.camerafactory.ui.theme.CameraFactoryTheme

@Preview
@Composable
fun MainCameraView(cameraViewModel: CameraViewModel = viewModel()) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val screenWidth = LocalConfiguration.current.screenWidthDp
    val cameraController = cameraViewModel.cameraController
    var isFrontCamera: Boolean by remember { mutableStateOf(false) }
    // 确保只有在需要使用摄像头时才执行摄像头初始化
    LaunchedEffect(lifecycleOwner) {
        // 设置其他属性
        cameraController.bindToLifecycle(lifecycleOwner)
    }
    CameraFactoryTheme {
        Surface(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.size(screenWidth.dp)
        ) {
            Column {
                // 相机预览
                Row (Modifier.fillMaxWidth()){
                    ViewCard(
                        modifier = Modifier.weight(1f,fill = false),
                        cameraController = cameraController,
                        cameraType = CameraType.ORIGINAL
                    )
                    SurfacePreviewCard(
                        modifier = Modifier.weight(1f),
                        cameraController = cameraController
                    )
//
//                    ComposeGLSurfaceView(
//                        cameraController = cameraController,
//                        )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier.fillMaxSize()) {
                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(16.dp),
                        contentColor = MaterialTheme.colorScheme.primary,

                        onClick = {
                            cameraController.cameraSelector = if (isFrontCamera) {
                                CameraSelector.DEFAULT_BACK_CAMERA
                            } else {
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            }
                            isFrontCamera = !isFrontCamera
                        }) {
                        Icon(painter = painterResource(R.drawable.party_mode),
                            contentDescription = "切换摄像头")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewCard(
    modifier: Modifier,
    cameraController: LifecycleCameraController,
    cameraType: CameraType
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceContainer,
        modifier = modifier.padding(4.dp).aspectRatio(1f / 1f),
        onClick = { showBottomSheet = true }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "预览效果",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = cameraType.description(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))


            when (cameraType) {
                CameraType.ORIGINAL -> {

                    ComposeCameraView(
                        cameraController = cameraController,
                        modifier = modifier,
                    )
                }

                CameraType.GREY -> TODO()
                CameraType.GALS -> TODO()
                CameraType.TEST -> Image(
                    painter = painterResource(R.drawable.test_pic),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)
                )
            }
            if (showBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier.fillMaxHeight(),
                    sheetState = sheetState,
                    onDismissRequest = { showBottomSheet = false }
                ) {
                    Text(
                        "Swipe up to open sheet. Swipe down to dismiss.",
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun SurfacePreviewCard(
    cameraController: LifecycleCameraController, modifier: Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.padding(4.dp).aspectRatio(1f / 1f)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "预览效果",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "灰度",
                    style = MaterialTheme.typography.bodyMedium,
                    textDecoration = TextDecoration.LineThrough
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            ComposeTextureView(cameraController = cameraController, modifier = modifier)
//            ComposeSurfaceView(cameraController = cameraController, modifier = modifier)
        }
    }


}