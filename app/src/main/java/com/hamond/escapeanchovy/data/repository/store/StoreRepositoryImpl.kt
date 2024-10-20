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
            throw Exception(e.message)
        }
    }
}