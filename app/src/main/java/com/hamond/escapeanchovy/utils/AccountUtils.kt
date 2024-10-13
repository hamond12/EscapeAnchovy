package com.hamond.escapeanchovy.utils

import android.content.Context
import androidx.core.content.edit
import com.hamond.escapeanchovy.constants.PrefsName.ACCOUNT_PREFS


object AccountUtils{

    const val UID = "uid"
    const val AUTO_LOGIN = "auto_login"

    // 로그인 시 호출
    fun saveUserEmail(context: Context, uid:String) {
        val prefs = context.getSharedPreferences(ACCOUNT_PREFS, 0)
        prefs.edit { putString(UID, uid) }
    }

    // 유저정보를 저장하거나 가져오는 로직에서 호출 (uid 기반으로 document 가져옴)
    fun getUserEmail(context: Context): String? {
        val prefs = context.getSharedPreferences(ACCOUNT_PREFS, 0)
        return prefs.getString(UID, null)
    }

    // 소셜 로그인이나 자동 로그인 설정 후 로그인할 떄 호출
    fun setAutoLogin(context: Context) {
        val prefs = context.getSharedPreferences(ACCOUNT_PREFS, 0)
        prefs.edit { putBoolean(AUTO_LOGIN, true) }
    }

    // 로그아웃할 때 호출
    fun cancelAutoLogin(context: Context) {
        val prefs = context.getSharedPreferences(ACCOUNT_PREFS, 0)
        prefs.edit { remove(AUTO_LOGIN) }
    }

    // 메인 액티비티에서 자동 로그인 여부 변수 초기화할 때 호출
    fun getAutoLogin(context: Context): Boolean {
        val prefs = context.getSharedPreferences(ACCOUNT_PREFS, 0)
        return prefs.getBoolean(AUTO_LOGIN, false)
    }
}