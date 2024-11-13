package com.opencv.camerafactory.FireBase

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.opencv.camerafactory.R
import com.opencv.camerafactory.Util.copyTokenToClipboard

open class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val channelId = "default_channel_id" // 设置通知通道ID

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
