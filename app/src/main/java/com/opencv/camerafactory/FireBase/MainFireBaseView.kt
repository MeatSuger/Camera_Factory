package com.opencv.camerafactory.FireBase

import android.content.Context
import android.content.res.Configuration
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
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import com.opencv.camerafactory.Util.copyTokenToClipboard

@Preview(
    showSystemUi = true, showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_NORMAL,
    wallpaper = Wallpapers.NONE
)
@Composable
fun MainFireBaseView(viewModel: FireBaseViewMode = viewModel()) {
    val context = LocalContext.current
    val screenWide = LocalConfiguration.current.screenWidthDp
    val screenHigh = LocalConfiguration.current.screenHeightDp
    var logText = viewModel.logText
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {

        Column {
            Column(
                modifier = Modifier.height((screenHigh * 0.4).dp),
                verticalArrangement = Arrangement.Center,

                ) {

                Text(
                    text = logText,
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,

                    )

            }


            Button(
                modifier = Modifier
                    .size(height = 60.dp, width = (screenWide.dp * 2) / 3)
                    .padding(4.dp),
                shape = RoundedCornerShape(20.dp),
                onClick = {  GetFireBaseToken(context) },
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    contentColor = MaterialTheme.colorScheme.onPrimary,
//                )
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
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    AnonymouslyLogin()
                },
            ) {
                Text(text = "匿名登陆到Firebase")
            }
            Spacer(modifier = Modifier.height(8.dp))

        }


    }


}

fun AnonymouslyLogin() {
    val firebaseAuth = FirebaseAuth.getInstance()
    firebaseAuth.signInAnonymously().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            Log.d("Firebase", "认证成功")
        } else {
            Log.d("Firebase", "认证失败", task.exception)
        }
    }
}


fun  GetFireBaseToken(context: Context) {

    var string = "getfiled"
    FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.w(
                "FCM", "Fetching FCM registration token failed", task.exception
            )
            string = task.exception.toString()
            return@OnCompleteListener
        }
        // Get new FCM registration token
        val token = task.result

        Log.d("FCM", token)
        string = token
        copyTokenToClipboard(context, token)
    })

}