package com.example.registrationcompose.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


object DataStoreUtils {

    inline fun <reified T> getPreferencesKey(key: String): Preferences.Key<*> {
        return when (T::class) {
            String::class -> stringPreferencesKey(key)
            Int::class -> intPreferencesKey(key)
            Boolean::class -> booleanPreferencesKey(key)
            Float::class -> floatPreferencesKey(key)
            Long::class -> longPreferencesKey(key)
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    suspend inline fun <reified T> saveData(dataStore: DataStore<Preferences>, key: String, value: T) {
        val preferencesKey = getPreferencesKey<T>(key)
        dataStore.edit { prefs ->
            when (value) {
                is String -> prefs[preferencesKey as Preferences.Key<String>] = value
                is Int -> prefs[preferencesKey as Preferences.Key<Int>] = value
                is Boolean -> prefs[preferencesKey as Preferences.Key<Boolean>] = value
                is Float -> prefs[preferencesKey as Preferences.Key<Float>] = value
                is Long -> prefs[preferencesKey as Preferences.Key<Long>] = value
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }
    }

    inline fun <reified T> getData(dataStore: DataStore<Preferences>, key: String, defaultValue: T): Flow<T> {
        val preferencesKey = getPreferencesKey<T>(key)
        return dataStore.data.map { prefs ->
            when (T::class) {
                String::class -> prefs[preferencesKey as Preferences.Key<String>] ?: defaultValue
                Int::class -> prefs[preferencesKey as Preferences.Key<Int>] ?: defaultValue
                Boolean::class -> prefs[preferencesKey as Preferences.Key<Boolean>] ?: defaultValue
                Float::class -> prefs[preferencesKey as Preferences.Key<Float>] ?: defaultValue
                Long::class -> prefs[preferencesKey as Preferences.Key<Long>] ?: defaultValue
                else -> throw IllegalArgumentException("Unsupported type")
            } as T
        }
    }
}
