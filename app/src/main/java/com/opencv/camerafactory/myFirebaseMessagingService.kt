package com.opencv.camerafactory

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

open class myFirebaseMessagingService : FirebaseMessagingService() {

    private val channelId = "default_channel_id" // 设置通知通道ID

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("FCM", "From: ${remoteMessage.from}")

        // 处理通知消息部分
        remoteMessage.notification?.let {
            Log.d("FCM", "Message Notification Body: ${it.body}")

            // 在此处处理通知内容，例如显示系统通知
            showNotification(it)
        }

        // 处理数据消息部分（如果有）
        remoteMessage.data.let {
            Log.d("FCM", "Message Data Payload: $it")
            // 在此处处理数据消息
        }
    }

    override fun onNewToken(token: String) {
        Log.d("FCM","The new Token is ${token}")
        copyTokenToClipboard(token)
    }

    @SuppressLint("ObsoleteSdkInt", "ResourceAsColor")
    private fun showNotification(message: RemoteMessage.Notification) {
        // 创建通知管理器
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 如果 Android 版本大于或等于 O（Android 8.0），需要创建通知渠道
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "转发FCM通知",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // 创建通知
        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.title)
            .setContentText(message.body)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setAutoCancel(true) // 点击通知后自动取消
            .build()

        // 显示通知
        notificationManager.notify(0, notification)
    }

     fun copyTokenToClipboard(token: String) {
        // 获取系统的剪贴板管理器
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        // 创建一个 ClipData 对象，其中包含要复制的 token
        val clip = ClipData.newPlainText("FCM Token", token)

        // 将数据放到剪贴板
        clipboard.setPrimaryClip(clip)

        Toast.makeText(applicationContext ,"${token} is copied to clipboard",Toast.LENGTH_SHORT).show()
    }
}
