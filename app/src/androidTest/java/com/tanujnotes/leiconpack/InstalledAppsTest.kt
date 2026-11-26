package com.tanujnotes.leiconpack

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.tanujnotes.leiconpack.util.findAppsWithoutIcons
import com.tanujnotes.leiconpack.util.readThemedComponents
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class InstalledAppsTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun readsThemedComponentsFromAppfilter() {
        val components = context.resources.readThemedComponents()
        assertTrue(components.size > 1000)
        assertTrue(components.all { it.startsWith("ComponentInfo{") })
    }

    @Test
    fun themedAppsAreNotReportedAsMissing() {
        val themed = context.resources.readThemedComponents()
        val missing = context.findAppsWithoutIcons()
        assertFalse(missing.any { "ComponentInfo{${it.componentName}}" in themed })
    }
}
