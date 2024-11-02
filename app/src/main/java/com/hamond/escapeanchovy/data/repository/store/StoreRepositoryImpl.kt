package com.hamond.escapeanchovy.data.repository.store

import com.google.firebase.firestore.FirebaseFirestore
import com.hamond.escapeanchovy.constants.Collection.USER
import com.hamond.escapeanchovy.data.model.User
import com.hamond.escapeanchovy.utils.AccountUtils.hashPassword
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val store: FirebaseFirestore
) : StoreRepository {

    override suspend fun saveAccountInfo(user: User) {
        try {
            store.collection(USER).document(user.email).set(user).await()
        } catch (e: Exception) {
            throw Exception("유저 정보 저장 에러: ${e.message}")
        }
    }

    override suspend fun isEmailDuplicate(email: String): Boolean {
        try {
            val query = store.collection(USER)
                .whereEqualTo("email", email).limit(1).get().await()
            return !query.isEmpty
        } catch (e: Exception) {
            throw Exception("이메일 중복 체크 에러: ${e.message}")
        }
    }

    override suspend fun isNameDuplicate(name: String): Boolean {
        try {
            val query = store.collection(USER)
                .whereEqualTo("name", name).limit(1).get().await()
            return !query.isEmpty
        } catch (e: Exception) {
            throw Exception("이메일 중복 체크 에러: ${e.message}")
        }
    }

    override suspend fun isLoginSuccess(email: String, password: String): Boolean {
        try {
            val query = store.collection(USER).whereEqualTo("email", email)
                .whereEqualTo("password", hashPassword(password)).limit(1).get().await()
            return !query.isEmpty
        } catch (e: Exception) {
            throw Exception("기본 로그인 에러: ${e.message}")
        }
    }
}