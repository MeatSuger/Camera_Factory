package com.opencv.camerafactory

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.opencv.camerafactory.Camera.ComposeCameraView
import com.opencv.camerafactory.Camera.ComposeTextureView
import com.opencv.camerafactory.Camera.ControlCamera
import com.opencv.camerafactory.FireBase.MainFireBaseView
import com.opencv.camerafactory.Util.NotificationPermission
import com.opencv.camerafactory.Util.getTextToShowGivenPermissions
import com.opencv.camerafactory.ui.theme.CameraFactoryTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalPermissionsApi::class)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            CameraFactoryTheme {
                NotificationPermission()
//                MainPage()
                Surface(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .systemBarsPadding()
                        .height(2400.dp)
                ) {
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

    override fun onStart() {
        super.onStart()
        // 测试 Firebase 认证
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInAnonymously().addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d("Firebase", "认证成功")
            } else {
                Log.d("Firebase", "认证失败", task.exception)
            }
        }
    }

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPage() {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // 相机按钮
                    IconButton(
                        onClick = {
                            if (currentRoute != "Camera") {  // 如果目标页面不是当前页面
                                navController.navigate("Camera")
                            }
                        }
                    ) {
                        Icon(Icons.Filled.AccountCircle, contentDescription = "相机")
                    }
                    // Firebase按钮
                    IconButton(
                        onClick = {
                            if (currentRoute != "FireBase") {  // 如果目标页面不是当前页面
                                navController.navigate("FireBase")
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Info, contentDescription = "FireBase")
                    }
                    // 设置按钮
                    IconButton(
                        onClick = {
                            if (currentRoute != "settings") {  // 如果目标页面不是当前页面
                                navController.navigate("settings")
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Settings, contentDescription = "设置")
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues),
            navController = navController,
            startDestination = "Camera"
        ) {
            composable("Camera") {
                ControlCamera()
            }
            composable("FireBase") {
                MainFireBaseView()
            }
            composable("settings") {
                // SettingsScreen()
            }
        }
    }

}

//@Preview

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ExampleCameraScreen() {
    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.POST_NOTIFICATIONS,
        )
    )


    if (multiplePermissionsState.allPermissionsGranted) {
        MainPage()
    } else {
        Column {
            Text(
                getTextToShowGivenPermissions(
                    multiplePermissionsState.revokedPermissions,
                    multiplePermissionsState.shouldShowRationale
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { multiplePermissionsState.launchMultiplePermissionRequest() }) {
                Text("Request permissions")
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
    uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true
)
@Composable
fun perCard() {
    CameraFactoryTheme {

//
//        Surface(
//            shape = MaterialTheme.shapes.medium,
//            color = MaterialTheme.colorScheme.primary,
//            modifier = Modifier.padding(4.dp)
//        ) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Row(verticalAlignment = Alignment.Bottom) {
//                    Text(
//                        text = "预览效果",
//                        style = MaterialTheme.typography.titleLarge,
//                        modifier = Modifier.padding(top = 4.dp)
//                    )
//                    Text(
//                        text = "灰度", style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//
//                Spacer(modifier = Modifier.height(4.dp))
//
//                Image(
//                    painter = painterResource(R.drawable.test_pic),
//                    contentDescription = null,
//                    modifier = Modifier.size(120.dp)
//
//                )
//            }
//        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            FloatingActionButton(modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd),
                onClick = { }) {
                Icon(Icons.AutoMirrored.Filled.ArrowForward, "Floating action button.")
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center, // 垂直方向居中
                horizontalAlignment = Alignment.CenterHorizontally // 水平方向居中
            ) {
                Text(
                    text = "Permission denied"
                )
                Spacer(modifier = Modifier.height(8.dp))

            }
        }


    }

}