package com.tanujnotes.leiconpack.ui

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tanujnotes.leiconpack.model.AppInfo
import com.tanujnotes.leiconpack.util.ACTION_REPORT_APP_CHOSEN
import com.tanujnotes.leiconpack.util.PrefUtil
import com.tanujnotes.leiconpack.util.findAppsWithoutIcons
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = PrefUtil(application)

    // Starts as true so the review prompt is never shown before the stored value is known.
    private val isReviewed = prefs.isReviewed
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = true)

    /** Installed apps without an icon in this pack. Every app starts out selected for the report. */
    var missingIconApps by mutableStateOf<List<AppInfo>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var showReviewDialog by mutableStateOf(false)
        private set

    /** Set when the user leaves to send a report, so we can ask for a review when they come back. */
    private var reviewPromptPending = false

    val hasSelection: Boolean
        get() = missingIconApps.any { it.checked }

    // A report counts as sent once the user picks an email app, not when the chooser opens.
    private val reportAppChosenReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            onReportSent()
        }
    }

    init {
        ContextCompat.registerReceiver(
            application,
            reportAppChosenReceiver,
            IntentFilter(ACTION_REPORT_APP_CHOSEN),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    override fun onCleared() {
        getApplication<Application>().unregisterReceiver(reportAppChosenReceiver)
    }

    /** Scans installed apps again, so newly installed apps show up and every app is selected. */
    fun loadMissingIconApps() {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            missingIconApps = withContext(Dispatchers.Default) {
                getApplication<Application>().findAppsWithoutIcons()
            }
            isLoading = false
        }
    }

    fun setChecked(componentName: String, checked: Boolean) {
        missingIconApps = missingIconApps.map {
            if (it.componentName == componentName) it.copy(checked = checked) else it
        }
    }

    fun buildMissingIconReport(): String = buildString {
        append("Manufacturer : ${Build.MANUFACTURER.uppercase()}\n")
        append("Model: ${Build.MODEL.uppercase()}\n\n")
        missingIconApps.filter { it.checked }.forEach { app ->
            append("Name: ${app.appName}\n ComponentInfo: ${app.componentName}\n\n")
        }
    }

    private fun onReportSent() {
        missingIconApps = missingIconApps.map { it.copy(checked = false) }
        reviewPromptPending = true
    }

    fun onResume() {
        if (!reviewPromptPending) return
        reviewPromptPending = false
        if (!isReviewed.value) showReviewDialog = true
    }

    fun onRateClicked() {
        showReviewDialog = false
        viewModelScope.launch { prefs.setIsReviewed() }
    }

    fun dismissReviewDialog() {
        showReviewDialog = false
    }
}
