package com.hamond.escapeanchovy.data.repository.kakaoLogin

import android.content.Context
import com.hamond.escapeanchovy.data.model.User

interface KakaoLoginRepository {
    suspend fun loginWithKakaoAccount(context: Context)
    suspend fun getKakaoUser(): User
}