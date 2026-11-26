package com.tanujnotes.leiconpack.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.tanujnotes.leiconpack.R

val Inter = FontFamily(Font(R.font.inter))

private val Default = Typography()

/** Material 3 type scale with Inter applied to every style. */
val Typography = Typography(
    displayLarge = Default.displayLarge.copy(fontFamily = Inter),
    displayMedium = Default.displayMedium.copy(fontFamily = Inter),
    displaySmall = Default.displaySmall.copy(fontFamily = Inter),
    headlineLarge = Default.headlineLarge.copy(fontFamily = Inter),
    headlineMedium = Default.headlineMedium.copy(fontFamily = Inter),
    headlineSmall = Default.headlineSmall.copy(fontFamily = Inter),
    titleLarge = Default.titleLarge.copy(fontFamily = Inter),
    titleMedium = Default.titleMedium.copy(fontFamily = Inter),
    titleSmall = Default.titleSmall.copy(fontFamily = Inter),
    bodyLarge = Default.bodyLarge.copy(fontFamily = Inter),
    bodyMedium = Default.bodyMedium.copy(fontFamily = Inter),
    bodySmall = Default.bodySmall.copy(fontFamily = Inter),
    labelLarge = Default.labelLarge.copy(fontFamily = Inter),
    labelMedium = Default.labelMedium.copy(fontFamily = Inter),
    labelSmall = Default.labelSmall.copy(fontFamily = Inter),
)
