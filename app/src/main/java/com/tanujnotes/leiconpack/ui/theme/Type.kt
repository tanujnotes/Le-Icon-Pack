package com.tanujnotes.leiconpack.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.tanujnotes.leiconpack.R

/** res/font/inter.ttf is a variable font, so each weight is the same file at another setting. */
private fun inter(weight: FontWeight) = Font(
    resId = R.font.inter,
    weight = weight,
    variationSettings = FontVariation.Settings(FontVariation.weight(weight.weight))
)

val Inter = FontFamily(
    inter(FontWeight.Normal),
    inter(FontWeight.Medium),
    inter(FontWeight.SemiBold),
)

private val Default = Typography()

private fun style(size: Int, lineHeight: Int, weight: FontWeight, letterSpacing: Double = 0.0) =
    TextStyle(
        fontFamily = Inter,
        fontWeight = weight,
        fontSize = size.sp,
        lineHeight = lineHeight.sp,
        letterSpacing = letterSpacing.em,
    )

/**
 * Inter throughout. The slots the screens use:
 * displayLarge for the app name on Home, headlineLarge for other screen titles, titleLarge for
 * section headings, titleMedium for list rows, bodyLarge for descriptions, bodyMedium for
 * captions and notes, labelLarge for buttons.
 */
val Typography = Typography(
    displayLarge = style(64, 64, FontWeight.SemiBold, -0.045),
    displayMedium = Default.displayMedium.copy(fontFamily = Inter),
    displaySmall = Default.displaySmall.copy(fontFamily = Inter),
    headlineLarge = style(40, 44, FontWeight.SemiBold, -0.035),
    headlineMedium = Default.headlineMedium.copy(fontFamily = Inter),
    headlineSmall = style(22, 28, FontWeight.SemiBold, -0.01),
    titleLarge = style(19, 26, FontWeight.SemiBold, -0.01),
    titleMedium = style(17, 24, FontWeight.Medium),
    titleSmall = Default.titleSmall.copy(fontFamily = Inter),
    bodyLarge = style(16, 24, FontWeight.Normal),
    bodyMedium = style(14, 20, FontWeight.Normal),
    bodySmall = style(13, 18, FontWeight.Normal),
    labelLarge = style(17, 24, FontWeight.SemiBold),
    labelMedium = Default.labelMedium.copy(fontFamily = Inter),
    labelSmall = Default.labelSmall.copy(fontFamily = Inter),
)
