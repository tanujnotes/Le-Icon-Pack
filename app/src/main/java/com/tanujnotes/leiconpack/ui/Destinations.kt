package com.tanujnotes.leiconpack.ui

/** Screens in the nav graph. Routes are stable identifiers, independent of the displayed titles. */
enum class Destination(val route: String) {
    Home("home"),
    Apply("apply"),
    WhyLetters("why_letters"),
    MissingIcons("missing_icons"),
}
