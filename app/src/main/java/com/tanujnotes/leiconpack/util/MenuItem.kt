package com.tanujnotes.leiconpack.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector

sealed class MenuItem(val title:String, val icon:ImageVector){
    object Home:MenuItem("Dashboard", Icons.Default.Home)
    object Apply:MenuItem("Apply", Icons.Default.Done)
    object CustomIcons:MenuItem("Why Le Icons", Icons.Filled.Warning)
}
