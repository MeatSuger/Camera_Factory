package com.opencv.camerafactory.Firebase

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.opencv.camerafactory.Util.copyTokenToClipboard

open class MyFirebaseMessagingService : FirebaseMessagingService() {



    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCM", "From: ${remoteMessage.from}")

        // 处理通知消息部分
        remoteMessage.notification?.let {
            Log.d("FCM", "Message Notification Body: ${it.body}")

            // 在此处处理通知内容，例如显示系统通知
//            showNotification(it)
        }

        // 处理数据消息部分（如果有）
        remoteMessage.data.let {
            Log.d("FCM", "Message Data Payload: $it")
            // 在此处处理数据消息
        }
    }

    override fun onNewToken(token: String) {
        Log.d("FCM", "The new Token is $token")
       copyTokenToClipboard(this,token)
    }


}
