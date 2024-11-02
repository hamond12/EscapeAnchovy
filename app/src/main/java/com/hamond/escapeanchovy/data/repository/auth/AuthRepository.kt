package com.hamond.escapeanchovy.data.repository.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun createTempAccount(email: String): AuthResult
    fun sendEmailVerification(user: FirebaseUser?)
    fun checkEmailVerification(): Boolean?
    fun loginTempAccount(email: String)
    fun deleteTempAccount()
}