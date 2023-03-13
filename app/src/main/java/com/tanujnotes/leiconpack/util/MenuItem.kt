package com.tanujnotes.leiconpack.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import com.tanujnotes.leiconpack.R

sealed class MenuItem(val title:String, val icon:Int){
    object Home:MenuItem("Dashboard", R.drawable.ic_home)
    object Apply:MenuItem("Apply", R.drawable.ic_done)
    object CustomIcons:MenuItem("Why Le Icons", R.drawable.ic_info)
}
