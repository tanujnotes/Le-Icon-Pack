package com.tanujnotes.leiconpack

import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tanujnotes.leiconpack.ui.LeIconPackApp
import com.tanujnotes.leiconpack.ui.theme.LeIconPackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // The top app bar uses the primary color, which is dark in the light theme and light in the
        // dark theme, so the status bar icons have to be the opposite of the system default.
        val isNightMode = resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        enableEdgeToEdge(
            statusBarStyle = if (isNightMode) {
                SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
            } else {
                SystemBarStyle.dark(Color.TRANSPARENT)
            }
        )
        super.onCreate(savedInstanceState)
        setContent {
            LeIconPackTheme {
                LeIconPackApp()
            }
        }
    }
}
