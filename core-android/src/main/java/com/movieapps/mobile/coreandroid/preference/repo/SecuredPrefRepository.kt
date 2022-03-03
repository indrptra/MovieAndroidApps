package com.movieapps.mobile.coreandroid.preference.repo

import android.content.SharedPreferences

interface SecuredPrefRepository : SharedPreferences, SharedPreferenceInterface {
    var accessToken: String
}
