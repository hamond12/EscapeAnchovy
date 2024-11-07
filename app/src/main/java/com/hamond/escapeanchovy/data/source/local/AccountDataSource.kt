package com.hamond.escapeanchovy.data.source.local

import android.content.Context
import androidx.core.content.edit
import com.hamond.escapeanchovy.constants.PrefsName.ACCOUNT_PREFS

object AccountDataSource{
    private const val EMAIL = "email"
    private const val AUTO_LOGIN = "auto_login"

    fun saveUserEmail(context: Context, email:String?) {
        val prefs = context.getSharedPreferences(ACCOUNT_PREFS, 0)
        prefs.edit { putString(EMAIL, email) }
    }

    fun getUserEmail(context: Context): String? {
        val prefs = context.getSharedPreferences(ACCOUNT_PREFS, 0)
        return prefs.getString(EMAIL, null)
    }

    fun saveAutoLogin(context: Context, isAutoLogin: Boolean) {
        val prefs = context.getSharedPreferences(ACCOUNT_PREFS, 0)
        prefs.edit { putBoolean(AUTO_LOGIN, isAutoLogin) }
    }

    fun getAutoLogin(context: Context): Boolean {
        val prefs = context.getSharedPreferences(ACCOUNT_PREFS, 0)
        return prefs.getBoolean(AUTO_LOGIN, false)
    }
}