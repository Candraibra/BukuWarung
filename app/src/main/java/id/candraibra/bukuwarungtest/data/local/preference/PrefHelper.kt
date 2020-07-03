package id.candraibra.bukuwarungtest.data.local.preference

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import id.candraibra.bukuwarungtest.R
import id.candraibra.bukuwarungtest.base.ThisApplication.Companion.app

object PrefHelper {

    private val appName = app.getString(R.string.app_name)
    private val editor: SharedPreferences.Editor
        get() = preferences.edit()
    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key -> listeners.forEach { it(key) } }
    private val listeners = mutableListOf<(String) -> Unit>()
    private val preferences = app.getSharedPreferences(appName, MODE_PRIVATE).apply {
        registerOnSharedPreferenceChangeListener(listener)
    }

    fun setString(key: PrefKey, value: String) = editor.putString(key.toString(), value).apply()
    fun getString(key: PrefKey): String = preferences.getString(key.toString(), "") ?: ""
    fun setInt(key: PrefKey, value: Int) = editor.putInt(key.toString(), value).apply()
    fun getInt(key: PrefKey): Int = preferences.getInt(key.toString(), 0)
    fun setBoolean(key: PrefKey, value: Boolean) = editor.putBoolean(key.toString(), value).apply()
    fun getBoolean(key: PrefKey): Boolean = preferences.getBoolean(key.toString(), false)
    fun clearPreference(key: PrefKey) = editor.remove(key.toString()).apply()
    fun clearAllPreferences() = editor.clear().apply()
    fun addListener(listener: (String) -> Unit) = listeners.add(listener)
    fun removeListener(listener: (String) -> Unit) = listeners.remove(listener)

}
