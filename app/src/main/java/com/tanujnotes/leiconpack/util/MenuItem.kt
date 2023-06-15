package com.tanujnotes.leiconpack.util

import com.tanujnotes.leiconpack.R

sealed class MenuItem(val title: String, val icon: Int) {
    object Home : MenuItem("Home", R.drawable.ic_home)
    object Apply : MenuItem("Apply", R.drawable.ic_apply)
    object WhyLeIcons : MenuItem("Why Le Icons", R.drawable.ic_info)
    object IconRequest : MenuItem("Missing Icons", R.drawable.ic_report)
}
