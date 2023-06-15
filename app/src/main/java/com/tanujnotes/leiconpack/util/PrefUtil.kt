package com.tanujnotes.leiconpack.util

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.tanujnotes.leiconpack.datastore
import kotlinx.coroutines.flow.map

class PrefUtil(private val context: Context) {
    companion object {
        val isReviewKey = booleanPreferencesKey("keyReview")
    }

    val isReviewed = context.datastore.data.map {
        it[isReviewKey] ?: false
    }

    suspend fun setIsReviewed() = context.datastore.edit {
        it[isReviewKey] = true
    }
}