package com.hamond.escapeanchovy.data.repository.naverLogin

import android.content.Context
import com.hamond.escapeanchovy.data.model.User

interface NaverLoginRepository {
    suspend fun loginWithNaverAccount(context: Context): String?
    suspend fun getNaverUser(accessToken: String?): User
}