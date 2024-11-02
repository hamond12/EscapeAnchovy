package com.hamond.escapeanchovy.data.repository.store

import com.google.firebase.firestore.FirebaseFirestore
import com.hamond.escapeanchovy.constants.Collection.USER
import com.hamond.escapeanchovy.data.model.User
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

    override suspend fun isNameDuplicate(name:String): Boolean{
        try{
            val query = store.collection(USER)
                .whereEqualTo("name", name).limit(1).get().await()
            return !query.isEmpty
        } catch (e: Exception) {
            throw Exception("이메일 중복 체크 에러: ${e.message}")
        }
    }
}