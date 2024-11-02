package com.hamond.escapeanchovy.data.repository.login

interface LoginRepository {
    fun saveAutoLogin(isAutoLogin: Boolean)
    fun getAutoLogin(): Boolean
    suspend fun isLoginSuccess(email: String, password: String): Boolean
}