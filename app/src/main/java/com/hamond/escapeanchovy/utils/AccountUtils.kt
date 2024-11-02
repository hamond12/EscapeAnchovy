package com.hamond.escapeanchovy.utils

import android.content.Context
import android.util.Patterns
import androidx.core.content.edit
import com.hamond.escapeanchovy.constants.PrefsName.ACCOUNT_PREFS
import java.security.MessageDigest


object AccountUtils{
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidName(name: String): Boolean {
        val regex = "^[a-zA-Z0-9\\s가-힣ㄱ-ㅎㅏ-ㅣ]*$".toRegex()
        return regex.matches(name)
    }

    fun isValidPassword(password: String): Boolean {
        val regex =
            "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\|,.<>\\/?]).{8,20}$".toRegex()
        return regex.matches(password)
    }

    fun hashPassword(password: String): String {
        val data = password.toByteArray()
        val sha256 = MessageDigest.getInstance("SHA-256")
        val hashValue = sha256.digest(data)
        return hashValue.joinToString("") { "%02x".format(it) }
    }
}