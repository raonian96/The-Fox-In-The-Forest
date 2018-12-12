package com.raonstudio.foxforest

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import java.util.*

object SharedPreferenceManager {
    private lateinit var preference: SharedPreferences

    fun init(context: Context) {
        preference = PreferenceManager.getDefaultSharedPreferences(context)
    }

    var userId: String
        get() = preference.getString("userId", "")?.takeIf { it.isNotEmpty() }
            ?: UUID.randomUUID().toString().apply {
                preference.edit().putString("userId", this).apply()
            }
        private set(value) {}
}