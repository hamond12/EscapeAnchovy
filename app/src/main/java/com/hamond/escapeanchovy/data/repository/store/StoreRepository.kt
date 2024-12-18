package com.hamond.escapeanchovy.data.repository.store

import com.hamond.escapeanchovy.data.model.User

interface StoreRepository {
    suspend fun saveAccountInfo(user: User)
    suspend fun isEmailDuplicate(email: String): Boolean
    suspend fun isNameDuplicate(name:String): Boolean
    suspend fun isLoginSuccess(email: String, pw: String): Boolean
    suspend fun getEmailByName(name: String): String?
    suspend fun resetPwByEmail(email: String, pw: String)
}
