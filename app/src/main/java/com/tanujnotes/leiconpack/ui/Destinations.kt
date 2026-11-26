package com.tanujnotes.leiconpack.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tanujnotes.leiconpack.R

/** Screens in the nav graph. Routes are stable identifiers, independent of the displayed titles. */
enum class Destination(val route: String, @StringRes val title: Int) {
    Home("home", R.string.menu_home),
    WhyLetters("why_letters", R.string.why_icons_label),
    MissingIcons("missing_icons", R.string.missing_icons_title);

    companion object {
        fun fromRoute(route: String?): Destination = entries.firstOrNull { it.route == route } ?: Home
    }
}

/** Entries shown in the navigation drawer. */
enum class DrawerItem(@StringRes val title: Int, @DrawableRes val icon: Int) {
    Home(R.string.menu_home, R.drawable.ic_home),
    Apply(R.string.menu_apply, R.drawable.ic_apply),
    WhyLetters(R.string.why_icons_label, R.drawable.ic_info),
}
