package com.opencv.camerafactory

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.opencv.camerafactory.Camera.ComposeTextureView
import com.opencv.camerafactory.Camera.MainCameraView
import com.opencv.camerafactory.FireBase.MainFireBaseView
import com.opencv.camerafactory.Util.NotificationPermission
import com.opencv.camerafactory.Util.RequestPermissions
import com.opencv.camerafactory.ui.theme.CameraFactoryTheme

class MainActivity : ComponentActivity() {


    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            CameraFactoryTheme {
                NotificationPermission()
                Surface(
                    modifier = Modifier
                        .background(Color.Transparent)
                        .systemBarsPadding()
                        .fillMaxHeight()
                ) {
//                    WindowCompat.setDecorFitsSystemWindows(window, true)
//                    //将窗口标志设置为布局无限制
//                    window.setFlags(
//                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//                    )
                    // Example camera screen
                    RequestPermissions()
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

@Preview(showBackground = true, showSystemUi = true)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPage() {
    val navController = rememberNavController()
    val isPreview = LocalInspectionMode.current
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Songs", "Artists", "Playlists")
    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            when (index) {
                                0 -> Icon(Icons.Filled.Info, contentDescription = item)
                                1 -> Icon(Icons.Filled.AccountCircle, contentDescription = item)
                                else -> Icon(Icons.Filled.Settings, contentDescription = item)
                            }
                        },
                        label = { Text(item) },
                        selected = selectedItem == index,
                        onClick = {

                            if (selectedItem != index) {  // 只在第一次选中时运行
                                selectedItem = index
                                navController.navigate(item)
                            }
                        },
                        alwaysShowLabel = false
                    )
                }
            }
        },

        ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(paddingValues).fillMaxHeight(),
            navController = navController,
            startDestination = items[0],
        ) {
            composable(items[0], enterTransition = {
                // 进入时使用淡入 + 从左侧滑入
                fadeIn(
                    animationSpec = tween(300, easing = LinearEasing) // 淡入动画
                ) + slideIntoContainer(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy, // 中等弹性
                        stiffness = Spring.StiffnessLow // 低刚度
                    ),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }, exitTransition = {
                // 退出时使用淡出 + 从右侧滑出
                fadeOut(
                    animationSpec = tween(
                        300, easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            }) {
                if (isPreview) {
                    testperviewcard()
                } else {
                    MainCameraView()
                }
            }

            composable(items[1], enterTransition = {
                // 进入时使用淡入 + 从左侧滑入
                fadeIn(animationSpec = tween(300, easing = LinearEasing)) + slideIntoContainer(
                    animationSpec = tween(300, easing = EaseIn),
                    towards = AnimatedContentTransitionScope.SlideDirection.Start
                )
            }, exitTransition = {
                // 退出时使用淡出 + 从右侧滑出
                fadeOut(
                    animationSpec = tween(
                        300,
                        easing = LinearEasing
                    )
                ) + slideOutOfContainer(
                    animationSpec = tween(300, easing = EaseOut),
                    towards = AnimatedContentTransitionScope.SlideDirection.End
                )
            }) {
                MainFireBaseView()
            }

            composable(items[2]) {
                // SettingsScreen()
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


        MainPage()


    }

}

@Composable
fun testperviewcard() {
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

