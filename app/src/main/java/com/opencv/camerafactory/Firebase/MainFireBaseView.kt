package com.opencv.camerafactory.Firebase

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.opencv.camerafactory.Util.copyTokenToClipboard


@Composable
fun MainFireBaseView(viewModel: FireBaseViewMode = viewModel()) {
    val context = LocalContext.current
    val screenWide = LocalConfiguration.current.screenWidthDp
    val screenHigh = LocalConfiguration.current.screenHeightDp
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val firebaseAuth = FirebaseAuth.getInstance()


    firebaseAuth.signInAnonymously().addOnCompleteListener(fun(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            Log.d("Firebase", "认证成功")
        } else {
            Log.d("Firebase", "认证失败", task.exception)
        }
    })
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .padding(4.dp),
    ) {
        Column {
            Column(
                modifier = Modifier.height((screenHigh * 0.4).dp),
                verticalArrangement = Arrangement.Center,

                ) {

                Text(
                    text = viewModel.logText,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                )
            }
            Button(
                modifier = Modifier
                    .size(height = 60.dp, width = (screenWide.dp * 2) / 3)
                    .padding(4.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors( // 使用 ButtonDefaults.buttonColors
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                onClick = {
                    getFireBaseToken(context) { token, error ->
                        if (token != null) {
                            viewModel.updateLogText(token)
                        }
                        errorMessage = error
                    }
                },
            ) {
                Text(
                    text = "获取Token并复制到剪贴板"
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                modifier = Modifier
                    .size(height = 60.dp, width = (screenWide.dp * 2) / 3)
                    .padding(4.dp),
                colors = ButtonDefaults.buttonColors( // 使用 ButtonDefaults.buttonColors
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    anonymouslyLogin()
                },
            ) {
                Text(text = "匿名登陆到Firebase")
            }
            Spacer(modifier = Modifier.height(8.dp))
            errorMessage?.let {
                Text(text = it, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}

fun anonymouslyLogin() {
    val firebaseAuth = FirebaseAuth.getInstance()
    firebaseAuth.signInAnonymously().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.d("FCM", "认证成功")
        } else {
            Log.d("FCM", "认证失败", task.exception)
        }
    }
}

fun getFireBaseToken(context: Context, onResult: (token: String?, error: String?) -> Unit) {
    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w("FCM", "Fetching FCM registration token failed", task.exception)
            onResult(null, "获取Token失败: ${task.exception?.message}")
            return@addOnCompleteListener
        }
        // 获取新的 FCM 注册 token
        val token = task.result
        Log.d("FCM", token)
        copyTokenToClipboard(context, token)
        onResult(token, null)  // 调用回调，返回token
    }
}