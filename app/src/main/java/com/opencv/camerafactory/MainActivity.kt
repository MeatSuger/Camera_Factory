package com.opencv.camerafactory

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Range
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalLensFacing
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.opencv.camerafactory.ui.theme.CameraFactoryTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CameraFactoryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .systemBarsPadding()
                        .height(2400.dp)
                ) {

                    // 将 décor fits system 窗口设置为 false
                    WindowCompat.setDecorFitsSystemWindows(window, true)
                    //将窗口标志设置为布局无限制
                    window.setFlags(
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                    )
                    // Example camera screen
                    ExampleCameraScreen()
                }
            }
        }
    }
}

@SuppressLint("InlinedApi")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ExampleCameraScreen() {
    // 相机权限
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    // 通知权限，仅在 Android 13 及以上版本请求
    val notificationPermissionState =
        rememberPermissionState(android.Manifest.permission.POST_NOTIFICATIONS)


    LaunchedEffect(Unit) {
        // 请求相机权限
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        }

        // 请求通知权限（仅限 Android 13 及以上版本）
        notificationPermissionState.let {
            if (!it.status.isGranted) {
                it.launchPermissionRequest()
            }
        }
    }

    when {
        // 相机权限已授权时，检查通知权限
        cameraPermissionState.status.isGranted -> {
            if (notificationPermissionState.status?.isGranted == true || Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                // 如果通知权限已授权或不需要请求通知权限（低于 Android 13）
                ControlCamera()
            } else {
                // 显示请求通知权限的界面或说明
                if (notificationPermissionState != null) {
                    NoNotificationPermissionScreen(notificationPermissionState)
                }
            }
        }

        else -> {
            // 未授权相机权限，显示未授权页面
            NoCameraPermissionScreen(cameraPermissionState)
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NoCameraPermissionScreen(cameraPermissionState: PermissionState) {


    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        val textToShow = if (cameraPermissionState.status.shouldShowRationale) {

            // 如果用户之前选择了拒绝该权限，应当向用户解释为什么应用程序需要这个权限
            "未获取相机授权将导致该功能无法正常使用。"
        } else {

            // 首次请求授权
            "该功能需要使用相机权限，请点击授权。"
        }
        Text(textToShow)
        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            cameraPermissionState.launchPermissionRequest()
        }) {
            Text("请求权限")
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NoNotificationPermissionScreen(notificationPermissionState: PermissionState) {


    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        val textToShow = if (notificationPermissionState.status.shouldShowRationale) {

            // 如果用户之前选择了拒绝该权限，应当向用户解释为什么应用程序需要这个权限
            "未获取通知授权将导致该功能无法正常使用。"
        } else {

            // 首次请求授权
            "该功能需要使用通知权限，请点击授权。"
        }
        Text(textToShow)
        Spacer(Modifier.height(8.dp))

        Button(onClick = {
            notificationPermissionState.launchPermissionRequest()
            Log.d("FCM", "NoNotificationPermissionScreen: 已点击")
        }) {
            Text("请求权限")
        }
    }
}


@androidx.annotation.OptIn(ExperimentalLensFacing::class)
//@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ControlCamera() {
    val context = LocalContext.current
    val lifeCycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current
    val cameraController = remember {
        LifecycleCameraController(context)
    }
    cameraController.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
    cameraController.imageAnalysisOutputImageFormat = ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888
    cameraController.imageAnalysisResolutionSelector =
        ResolutionSelector.Builder().setResolutionStrategy(
            ResolutionStrategy(
                android.util.Size(720, 720), ResolutionStrategy.FALLBACK_RULE_CLOSEST_HIGHER
            )
        ).build()
    cameraController.videoCaptureTargetFrameRate = Range(30, 30)
    cameraController.bindToLifecycle(lifeCycleOwner)
    val screenWide: Int = LocalConfiguration.current.screenWidthDp
    CameraFactoryTheme {
        Surface(
            shape = MaterialTheme.shapes.medium, modifier = Modifier.size(screenWide.dp)
        ) {
            Column {
                Row {
                    PreviewCard(
                        cameraController = cameraController,
                        modifier = Modifier.size(screenWide.dp / 2),
                    )


                }

                Button(
                    onClick = {
                        cameraController.cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    },
                    modifier = Modifier
                        .size(height = 50.dp, width = screenWide.dp)
                        .padding(4.dp),
                ) {
                    Text(
                        text = "后置摄像头", style = MaterialTheme.typography.bodyMedium
                    )
                }
                Button(
                    onClick = {
                        cameraController.cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA
                    },
                    modifier = Modifier
                        .size(height = 50.dp, width = screenWide.dp)
                        .padding(4.dp),
                ) {
                    Text(
                        text = "前置摄像头", style = MaterialTheme.typography.bodyMedium
                    )
                }
                Button(
                    onClick = {
                        FirebaseMessaging.getInstance().token.addOnCompleteListener(
                            OnCompleteListener { task ->
                                if (!task.isSuccessful) {
                                    Log.w(
                                        "FCM",
                                        "Fetching FCM registration token failed",
                                        task.exception
                                    )
                                    return@OnCompleteListener
                                }

                                // Get new FCM registration token
                                val token = task.result

                                Log.d("FCM", token)
                                Toast.makeText(context, token, Toast.LENGTH_SHORT).show()
                            })
                    },
                    modifier = Modifier
                        .size(height = 50.dp, width = screenWide.dp)
                        .padding(4.dp),
                ) {
                    Text(
                        text = "FCM测试", style = MaterialTheme.typography.bodyMedium
                    )
                }
                Button(
                    onClick = {
                        val intent = Intent(context, MyFirebaseMessagingService::class.java)
                        context.startService(intent)
                        Log.d("FCM", "Service is start")
                    },
                    modifier = Modifier
                        .size(height = 50.dp, width = screenWide.dp)
                        .padding(4.dp),
                ) {
                    Text(
                        text = "FCM服务启动", style = MaterialTheme.typography.bodyMedium
                    )
                }

//                SurfacePreviewCard(
//                    cameraController = cameraController,
//                    modifier = Modifier.size(screenWide.dp)
//                )
            }
        }
    }
}

@Composable
fun PreviewCard(
    cameraController: LifecycleCameraController, modifier: Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "预览效果",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = "无", style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            ComposeCameraView(
                cameraController = cameraController, modifier = modifier
            )
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
        modifier = Modifier.padding(4.dp)
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

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showSystemUi = false
)
@Composable
fun perCard() {
    CameraFactoryTheme {


        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(4.dp)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "预览效果",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                    Text(
                        text = "灰度", style = MaterialTheme.typography.bodyMedium
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Image(
                    painter = painterResource(R.drawable.test_pic),
                    contentDescription = null,
                    modifier = Modifier.size(120.dp)

                )
            }
        }
    }

}