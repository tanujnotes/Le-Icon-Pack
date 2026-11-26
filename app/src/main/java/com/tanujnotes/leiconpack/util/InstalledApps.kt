package com.tanujnotes.leiconpack.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import androidx.annotation.WorkerThread
import com.tanujnotes.leiconpack.R
import com.tanujnotes.leiconpack.model.AppInfo
import org.xmlpull.v1.XmlPullParser

/**
 * Component names (in `ComponentInfo{package/class}` form) that already have an icon in
 * res/xml/appfilter.xml. Special entries such as `:CALENDAR` are skipped.
 */
@WorkerThread
fun Resources.readThemedComponents(): Set<String> {
    val components = HashSet<String>()
    getXml(R.xml.appfilter).use { parser ->
        var event = parser.eventType
        while (event != XmlPullParser.END_DOCUMENT) {
            if (event == XmlPullParser.START_TAG && parser.name == "item") {
                parser.getAttributeValue(null, "component")
                    ?.trim()
                    ?.takeUnless { it.startsWith(":") }
                    ?.let(components::add)
            }
            event = parser.next()
        }
    }
    return components
}

/** Launchable activities on the device that this icon pack has no icon for, sorted by name. */
@WorkerThread
fun Context.findAppsWithoutIcons(): List<AppInfo> {
    val themedComponents = resources.readThemedComponents()
    val launcherIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
    val activities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.queryIntentActivities(
            launcherIntent,
            PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_ALL.toLong())
        )
    } else {
        @Suppress("DEPRECATION")
        packageManager.queryIntentActivities(launcherIntent, PackageManager.MATCH_ALL)
    }
    return activities
        .map { it.activityInfo }
        .filter { info ->
            val component = ComponentName(info.packageName, info.name)
            // appfilter entries may use either the full or the short (".Activity") class name.
            component.toString() !in themedComponents &&
                    "ComponentInfo{${component.flattenToShortString()}}" !in themedComponents
        }
        .map { info ->
            AppInfo(
                appIcon = info.loadIcon(packageManager),
                appName = info.loadLabel(packageManager).toString(),
                componentName = ComponentName(info.packageName, info.name).flattenToString(),
            )
        }
        .sortedBy { it.appName.lowercase() }
}
