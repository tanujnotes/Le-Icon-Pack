package com.tanujnotes.leiconpack.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.datastore: DataStore<Preferences> by preferencesDataStore("settings")

class PrefUtil(context: Context) {
    private val datastore = context.applicationContext.datastore

    val isReviewed: Flow<Boolean> = datastore.data.map { it[IS_REVIEWED_KEY] ?: false }

    suspend fun setIsReviewed() {
        datastore.edit { it[IS_REVIEWED_KEY] = true }
    }

    private companion object {
        val IS_REVIEWED_KEY = booleanPreferencesKey("keyReview")
    }
}
