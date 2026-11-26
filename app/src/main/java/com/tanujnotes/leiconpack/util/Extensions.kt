package com.tanujnotes.leiconpack.util

import android.app.PendingIntent
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.net.toUri
import com.tanujnotes.leiconpack.R

private const val PLAY_STORE_DEV_PAGE = "https://play.google.com/store/apps/dev?id=7198807840081074933"
private const val REPORT_EMAIL = "leiconpack@gmail.com"

/**
 * Broadcast sent (only to this app) when the user picks an email app for a missing icon report.
 * Closing the chooser without picking one sends nothing.
 */
const val ACTION_REPORT_APP_CHOSEN = "com.tanujnotes.leiconpack.action.REPORT_APP_CHOSEN"

private val Context.playStoreWebUrl: String
    get() = "https://play.google.com/store/apps/details?id=$packageName"

private const val EXTERNAL_PAGE_FLAGS = Intent.FLAG_ACTIVITY_NO_HISTORY or
        Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
        Intent.FLAG_ACTIVITY_MULTIPLE_TASK

/** Starts [intent], returning false instead of crashing when nothing can handle it. */
private fun Context.tryStartActivity(intent: Intent): Boolean = try {
    startActivity(intent)
    true
} catch (e: ActivityNotFoundException) {
    false
}

fun Context.showToast(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.openPlayStore() {
    val opened = tryStartActivity(
        Intent(Intent.ACTION_VIEW, "market://details?id=$packageName".toUri())
            .addFlags(EXTERNAL_PAGE_FLAGS)
    ) || tryStartActivity(
        Intent(Intent.ACTION_VIEW, playStoreWebUrl.toUri()).addFlags(EXTERNAL_PAGE_FLAGS)
    )
    if (!opened) showToast(R.string.no_app_to_open_link)
}

fun Context.openPlayStoreDevPage() {
    val opened = tryStartActivity(
        Intent(Intent.ACTION_VIEW, PLAY_STORE_DEV_PAGE.toUri()).addFlags(EXTERNAL_PAGE_FLAGS)
    )
    if (!opened) showToast(R.string.no_app_to_open_link)
}

fun Context.openShareIntent() {
    val sendIntent = Intent(Intent.ACTION_SEND)
        .setType("text/plain")
        .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_message, playStoreWebUrl))
    tryStartActivity(Intent.createChooser(sendIntent, getString(R.string.share_chooser_title)))
}

/**
 * Lets the user pick an email app for [report], addressed to the icon request inbox. When they
 * pick one, [ACTION_REPORT_APP_CHOSEN] is broadcast to this app.
 *
 * @return false if there is no email app to show.
 */
fun Context.sendMissingIconReport(report: String): Boolean {
    val emailIntent = Intent(Intent.ACTION_SENDTO, "mailto:".toUri())
        .putExtra(Intent.EXTRA_EMAIL, arrayOf(REPORT_EMAIL))
        .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.report_email_subject))
        .putExtra(Intent.EXTRA_TEXT, report)
    if (emailIntent.resolveActivity(packageManager) == null) return false
    // Mutable so the chooser can add the chosen app to it; the package keeps it within this app.
    val appChosen = PendingIntent.getBroadcast(
        this,
        0,
        Intent(ACTION_REPORT_APP_CHOSEN).setPackage(packageName),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
    )
    return tryStartActivity(
        Intent.createChooser(
            emailIntent,
            getString(R.string.report_email_chooser_title),
            appChosen.intentSender
        )
    )
}
