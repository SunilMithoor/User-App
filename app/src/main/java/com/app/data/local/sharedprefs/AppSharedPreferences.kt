package com.app.data.local.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import com.app.data.utils.Constants.PREF
import com.app.data.utils.Constants.USER_LOGGED_IN
import com.app.data.utils.Constants.USER_TOKEN
import com.squareup.moshi.Moshi

class AppSharedPreferences(val context: Context, val moshi: Moshi) {


    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF, Context.MODE_PRIVATE)


    inline fun <reified T : Any> getValueModel(key: String): T? {
        getValueString(key)?.let {
            return moshi.adapter(T::class.java).fromJson(it)
        }
        return null
    }

    inline fun <reified T : Any> saveModel(key: String, model: T) {
        val jsonData: String = moshi.adapter(T::class.java).toJson(model)
        setValue(key, jsonData)
    }

    fun setValue(key: String, value: Any) {
        when (value) {
            is String -> sharedPreferences.edit().putString(key, value).apply()
            is Boolean -> sharedPreferences.edit().putBoolean(key, value).apply()
            is Int -> sharedPreferences.edit().putInt(key, value).apply()
        }
    }

    fun getValueString(key: String): String? = sharedPreferences.getString(key, null)
    fun getValueBoolean(key: String): Boolean = sharedPreferences.getBoolean(key, false)


    fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPreferences.getString(key, "")
            Boolean::class.java -> sharedPreferences.getBoolean(key, false)
            Float::class.java -> sharedPreferences.getFloat(key, -1f)
            Double::class.java -> sharedPreferences.getFloat(key, -1f)
            Int::class.java -> sharedPreferences.getInt(key, -1)
            Long::class.java -> sharedPreferences.getLong(key, -1L)
            else -> null
        } as T


    fun <T> put(key: String, data: T) {
        val editor = sharedPreferences.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }

    private fun <T> removeKey(key: String) {
        val editor = sharedPreferences.edit()
        editor.remove(key).apply()
    }


    fun clear() {
        sharedPreferences.edit().run {
            remove(USER_TOKEN)
            remove(USER_LOGGED_IN)
        }.apply()
    }

    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }

    fun saveToken(token: String) {
        put(USER_TOKEN, token)
    }

    fun getToken(): String {
        return get(USER_TOKEN, String::class.java)
    }

    fun isUserLoggedIn(): Boolean {
        return get(USER_LOGGED_IN, Boolean::class.java)
    }

    fun saveUserLoggedIn(loginStatus: Boolean) {
        put(USER_LOGGED_IN, loginStatus)
    }

}