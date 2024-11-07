package com.hamond.escapeanchovy.data.repository.auth

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.hamond.escapeanchovy.constants.Secret.TEMP_PASSWORD
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun createTempAccount(email: String): AuthResult {
        try {
            return auth.createUserWithEmailAndPassword(email, TEMP_PASSWORD).await()
        } catch (e: Exception) {
            throw Exception("임시 계정 생성 오류: ${e.message}")
        }
    }

    override fun sendEmailVerification(user: FirebaseUser?) {
        try {
            user?.sendEmailVerification()
        } catch (e: Exception) {
            throw Exception("이메일 인증 수신 오류: ${e.message}")
        }
    }

    override fun checkEmailVerification(): Boolean? {
        try {
            val user = auth.currentUser
            user?.reload()
            return user?.isEmailVerified
        } catch (e: Exception) {
            throw Exception("이메일 인증 확인 오류: ${e.message}")
        }
    }

    override fun loginTempAccount(email: String) {
        try {
            auth.signInWithEmailAndPassword(email, TEMP_PASSWORD)
        } catch (e: Exception) {
            throw Exception("임시계정 로그인 오류: ${e.message}")
        }
    }

    override fun deleteTempAccount() {
        try {
            auth.currentUser?.delete()
        } catch (e: Exception) {
            throw Exception("임시 계정 삭제 오류: ${e.message}")
        }
    }
}