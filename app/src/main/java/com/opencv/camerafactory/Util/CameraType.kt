package com.opencv.camerafactory.Util

sealed class CameraType {
    data object ORIGINAL : CameraType() // 原始视图
    data object GREY : CameraType()     // 灰度视图
    data object GALS : CameraType()      // 高斯模糊效果视图
    data object TEST : CameraType()      // 高斯模糊效果视图
    companion object {
        fun values(): Array<CameraType> {
            return arrayOf(ORIGINAL, GREY, GALS)
        }

        fun valueOf(value: String): CameraType {
            return when (value) {
                "ORIGINAL" -> ORIGINAL
                "GREY" -> GREY
                "GALS" -> GALS
                "TEST" -> TEST
                else -> throw IllegalArgumentException("No object com.opencv.camerafactory.Util.CameraType.$value")
            }
        }

    }

    fun description(): String {
        return when (this) {
            ORIGINAL -> "原始视图" // 不做任何图像处理
            GREY -> "灰度视图"    // 灰度处理
            GALS -> "高斯模糊效果视图" // 高斯模糊效果
            TEST -> "测试"
            else -> "未知视图"
        }
    }
}