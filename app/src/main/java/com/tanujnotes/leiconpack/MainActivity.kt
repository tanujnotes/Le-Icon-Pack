package com.tanujnotes.leiconpack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.tanujnotes.leiconpack.ui.LeIconPackApp
import com.tanujnotes.leiconpack.ui.theme.LeIconPackTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Every screen draws on the theme's background, so the system bars follow dark mode.
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            LeIconPackTheme {
                LeIconPackApp()
            }
        }
    }
}
