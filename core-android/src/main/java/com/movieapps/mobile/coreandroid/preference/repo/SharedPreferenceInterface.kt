package com.movieapps.mobile.coreandroid.preference.repo

import android.content.SharedPreferences

interface SharedPreferenceInterface {
    fun edit(): SharedPreferences.Editor

    fun <T : Any?> set(key: String, value: T)

    fun getString(key: String, defaultValue: String?): String?

    fun getInt(key: String, defaultValue: Int): Int

    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    fun getLong(key: String, defaultValue: Long): Long

    fun getFloat(key: String, defaultValue: Float): Float

    fun contains(key: String): Boolean

    fun remove(key: String)

    fun clear()
}