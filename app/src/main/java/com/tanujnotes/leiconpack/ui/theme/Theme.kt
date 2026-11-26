package com.tanujnotes.leiconpack.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = LightInk,
    onPrimary = Color.White,
    secondary = LightInk,
    onSecondary = Color.White,
    background = LightGround,
    onBackground = LightInk,
    surface = LightGround,
    onSurface = LightInk,
    surfaceVariant = LightWell,
    onSurfaceVariant = LightInkMuted,
    surfaceTint = LightGround,
    surfaceContainerHigh = LightDialog,
    outline = LightInkMuted,
    outlineVariant = LightDivider,
)

private val DarkColors = darkColorScheme(
    primary = DarkInk,
    onPrimary = DarkGround,
    secondary = DarkInk,
    onSecondary = DarkGround,
    background = DarkGround,
    onBackground = DarkInk,
    surface = DarkGround,
    onSurface = DarkInk,
    // The pack's night icons are dark tiles that already stand out on the dark ground.
    surfaceVariant = DarkGround,
    onSurfaceVariant = DarkInkMuted,
    surfaceTint = DarkGround,
    surfaceContainerHigh = DarkDialog,
    outline = DarkInkMuted,
    outlineVariant = DarkDivider,
)

@Composable
fun LeIconPackTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (useDarkTheme) DarkColors else LightColors,
        typography = Typography,
        content = content
    )
}
