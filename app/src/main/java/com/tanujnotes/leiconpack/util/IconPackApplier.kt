package com.tanujnotes.leiconpack.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build

/** Package names of launchers that can apply an icon pack directly through an intent. */
private object LauncherPackages {
    const val NOVA = "com.teslacoilsw.launcher"
    const val NIAGARA = "bitpit.launcher"
    const val APEX = "com.anddoes.launcher"
    const val LAWNCHAIR = "ch.deletescape.lawnchair.plah"
    const val ACTION = "com.actionlauncher.playstore"
    const val SMART_FREE = "ginlemon.flowerfree"
    const val SMART_PRO = "ginlemon.flowerpro"
    const val SMART_SPECIAL = "ginlemon.flowerpro.special"
    const val GO = "com.gau.go.launcherex"
    const val HYPERION = "projekt.launcher"
    const val PIXEL = "com.tushar.launcher3"
    const val POCO = "com.mi.android.globallauncher"
    const val TOTAL = "com.ss.launcher2"
    const val GOOGLE_PIXEL = "com.google.android.apps.nexuslauncher"
    const val LYNX = "org.n277.lynxlauncher"
}

/**
 * Tries to apply this icon pack in the user's default launcher.
 *
 * @return true if the launcher was asked to apply the pack, false if the launcher isn't supported
 * (or couldn't be reached) and the user should be shown manual instructions instead.
 */
fun Context.applyIconPack(): Boolean {
    val launcher = defaultLauncherPackage() ?: return false
    val iconPack = packageName
    val intent: Intent = when (launcher) {
        LauncherPackages.NOVA -> Intent("com.teslacoilsw.launcher.APPLY_ICON_THEME")
            .setPackage(launcher)
            .putExtra("com.teslacoilsw.launcher.extra.ICON_THEME_PACKAGE", iconPack)

        LauncherPackages.NIAGARA -> Intent("bitpit.launcher.APPLY_ICONS")
            .setPackage(launcher)
            .putExtra("packageName", iconPack)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        LauncherPackages.APEX -> Intent("com.anddoes.launcher.SET_THEME")
            .putExtra("com.anddoes.launcher.THEME_PACKAGE_NAME", iconPack)

        LauncherPackages.LAWNCHAIR -> Intent("ch.deletescape.lawnchair.APPLY_ICONS")
            .setPackage(launcher)
            .putExtra("packageName", iconPack)

        LauncherPackages.ACTION -> packageManager.getLaunchIntentForPackage(launcher)
            ?.putExtra("apply_icon_pack", iconPack)
            ?: return false

        LauncherPackages.SMART_FREE,
        LauncherPackages.SMART_PRO,
        LauncherPackages.SMART_SPECIAL -> Intent("ginlemon.smartlauncher.setGSLTHEME")
            .putExtra("package", iconPack)

        LauncherPackages.GO -> {
            val launchIntent = packageManager.getLaunchIntentForPackage(launcher) ?: return false
            sendBroadcast(
                Intent("$launcher.MyThemes.mythemeaction")
                    .setPackage(launcher)
                    .putExtra("type", 1)
                    .putExtra("pkgname", iconPack)
            )
            launchIntent
        }

        LauncherPackages.HYPERION -> Intent("projekt.launcher.CHANGE_ICONS")
            .setPackage(launcher)
            .putExtra("className", "projekt.launcher.ProjektLauncher")

        LauncherPackages.PIXEL -> Intent("com.tushar.launcher3.APPLY_ICONS")
            .setPackage(launcher)
            .putExtra("packageName", iconPack)

        LauncherPackages.POCO -> Intent("com.mi.android.globallauncher.SET_ICON_PACK")
            .setPackage(launcher)
            .putExtra("pkg_name", iconPack)

        LauncherPackages.TOTAL -> Intent("com.ss.launcher2.APPLY_ICON_THEME")
            .putExtra("packageName", iconPack)

        LauncherPackages.GOOGLE_PIXEL -> Intent("com.google.android.apps.nexuslauncher.APPLY_ICON_PACK")
            .putExtra("com.google.android.apps.nexuslauncher.extra.ICON_PACK", iconPack)

        LauncherPackages.LYNX -> Intent("org.n277.lynxlauncher.SET_THEME")
            .setPackage(launcher)
            .putExtra("package", iconPack)

        else -> return false
    }
    return try {
        startActivity(intent)
        true
    } catch (e: ActivityNotFoundException) {
        false
    }
}

private fun Context.defaultLauncherPackage(): String? {
    val homeIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME)
    val resolveInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        packageManager.resolveActivity(
            homeIntent,
            PackageManager.ResolveInfoFlags.of(PackageManager.MATCH_DEFAULT_ONLY.toLong())
        )
    } else {
        @Suppress("DEPRECATION")
        packageManager.resolveActivity(homeIntent, PackageManager.MATCH_DEFAULT_ONLY)
    }
    return resolveInfo?.activityInfo?.packageName
}
