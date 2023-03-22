package com.tanujnotes.leiconpack.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val appIcon:Drawable,
    val appName:String,
    val componentName:String,
    val checked:Boolean = true
)
