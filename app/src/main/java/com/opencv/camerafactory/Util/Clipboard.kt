package com.opencv.camerafactory.Util

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.os.PersistableBundle
import android.widget.Toast


fun copyTokenToClipboard(context: Context, token: String) {
    // 获取系统的剪贴板管理器
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    // 创建一个 ClipData 对象，其中包含要复制的 token
    val clip = ClipData.newPlainText("FCM Token", token).apply {
        description.extras = PersistableBundle().apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                putBoolean(ClipDescription.EXTRA_IS_SENSITIVE, true)
            } else {
                putBoolean("android.content.extra.IS_SENSITIVE", true)
            }
        }
    }

    // 将数据放到剪贴板
    clipboard.setPrimaryClip(clip)

    // 显示提示 Toast
    Toast.makeText(context, "token is copied to clipboard", Toast.LENGTH_SHORT).show()
}
