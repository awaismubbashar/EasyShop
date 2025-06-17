package com.example.easyshop.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object UserPreferenceKeys {
    val NAME = stringPreferencesKey("name")
    val EMAIL = stringPreferencesKey("email")
    val PASSWORD = stringPreferencesKey("password")
}