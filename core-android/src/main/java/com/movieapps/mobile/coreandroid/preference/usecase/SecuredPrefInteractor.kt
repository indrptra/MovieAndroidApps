package com.movieapps.mobile.coreandroid.preference.usecase

import android.content.SharedPreferences
import com.movieapps.mobile.coreandroid.preference.repo.SecuredPrefRepository

class SecuredPrefInteractor(val pref: SecuredPrefRepository) : SecuredPrefUseCase {
    override var accessToken: String
        get() = pref.accessToken
        set(value) {
            pref.accessToken = value
        }

    override fun getAll(): MutableMap<String, *> {
        return pref.all
    }

    override fun getString(key: String?, defValue: String?): String? {
        return pref.getString(key, defValue)
    }

    override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String> {
        return pref.getStringSet(key, defValues) ?: mutableSetOf()
    }

    override fun getInt(key: String?, defValue: Int): Int {
        return pref.getInt(key, defValue)
    }

    override fun getLong(key: String?, defValue: Long): Long {
        return pref.getLong(key, defValue)
    }

    override fun getFloat(key: String?, defValue: Float): Float {
        return pref.getFloat(key, defValue)
    }

    override fun getBoolean(key: String?, defValue: Boolean): Boolean {
        return pref.getBoolean(key, defValue)
    }

    override fun contains(key: String?): Boolean {
        return pref.contains(key)
    }

    override fun edit(): SharedPreferences.Editor {
        return pref.edit()
    }

    override fun registerOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?,
    ) {
        pref.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun unregisterOnSharedPreferenceChangeListener(
        listener: SharedPreferences.OnSharedPreferenceChangeListener?,
    ) {
        pref.unregisterOnSharedPreferenceChangeListener(listener)
    }

    override fun <T> set(key: String, value: T) {
        pref.set(key, value)
    }

    override fun remove(key: String) {
        pref.remove(key)
    }

    override fun clear() {
        pref.clear()
    }
}