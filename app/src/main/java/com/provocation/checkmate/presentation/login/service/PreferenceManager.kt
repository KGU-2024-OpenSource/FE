package com.provocation.checkmate.presentation.login.service

import android.content.Context
import android.content.SharedPreferences

object PreferenceManager {
    private const val PREF_NAME = "AuthPrefs"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }
    fun saveJwtToken(context: Context, email: String, token: String) {
        val editor = getPreferences(context).edit()
        val key = getJwtKey(email)
        editor.putString(key, token)
        editor.apply()
    }

    fun getJwtToken(context: Context, email: String): String? {
        val key = getJwtKey(email)
        return getPreferences(context).getString(key, null)
    }

    fun clearJwtToken(context: Context, email:String) {
        val editor = getPreferences(context).edit()
        val key = getJwtKey(email)
        editor.remove(key)
        editor.apply()
    }

    private fun getJwtKey(email: String): String {
        return "JWT_TOKEN_$email"
    }

}