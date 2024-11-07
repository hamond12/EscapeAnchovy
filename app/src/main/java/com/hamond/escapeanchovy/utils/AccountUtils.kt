package com.hamond.escapeanchovy.utils

import android.util.Patterns
import com.hamond.escapeanchovy.data.repository.auth.AuthRepository
import kotlinx.coroutines.CoroutineScope
import java.security.MessageDigest

object AccountUtils{
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isValidName(name: String): Boolean {
        val regex = "^[a-zA-Z0-9\\s가-힣ㄱ-ㅎㅏ-ㅣ]*$".toRegex()
        return regex.matches(name)
    }

    fun isValidPw(password: String): Boolean {
        val regex =
            "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"|,.<>/?]).{8,20}$".toRegex()
        return regex.matches(password)
    }

    fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return String.format("%02d:%02d", minutes, remainingSeconds)
    }

    fun hashPw(pw: String): String {
        val data = pw.toByteArray()
        val sha256 = MessageDigest.getInstance("SHA-256")
        val hashValue = sha256.digest(data)
        return hashValue.joinToString("") { "%02x".format(it) }
    }
}