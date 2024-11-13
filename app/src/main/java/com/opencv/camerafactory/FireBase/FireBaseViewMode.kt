package com.opencv.camerafactory.FireBase

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class FireBaseViewMode:ViewModel() {

    var logText by mutableStateOf(" ")
        private set

    // 更新 logText 的方法
    fun updateLogText(newText: String) {
        logText = newText
    }
}