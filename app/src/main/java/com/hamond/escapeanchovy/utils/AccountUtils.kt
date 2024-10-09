package com.hamond.escapeanchovy.utils

import android.content.Context
import androidx.core.content.edit
import com.hamond.escapeanchovy.constants.PrefsName.ACCOUNT_PREFS


object AccountUtils{
    fun saveUid(context: Context, uid:String) {
        val prefs = context.getSharedPreferences(ACCOUNT_PREFS, 0)
        prefs.edit { putString("uid", uid) }
    }

    fun getUid(context: Context): String? {
        val prefs = context.getSharedPreferences(ACCOUNT_PREFS, 0)
        return prefs.getString("uid", null)
    }

    fun removeUid(context: Context) {
        val prefs = context.getSharedPreferences(ACCOUNT_PREFS, 0)
        prefs.edit { remove("uid") }
    }
}