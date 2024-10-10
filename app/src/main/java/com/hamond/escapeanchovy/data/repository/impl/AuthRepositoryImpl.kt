package com.hamond.escapeanchovy.data.repository.impl

import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hamond.escapeanchovy.domain.repository.AuthRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun googleLogin(result: GetCredentialResponse): Result<String> {
        val credential = result.credential
        return if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
            val idToken = googleIdTokenCredential.idToken
            val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)

            try {
                val authResult = firebaseAuth.signInWithCredential(firebaseCredential).await()
                Result.success(authResult.user?.uid ?: throw Exception("User ID not found"))
            } catch (e: Exception) {
                Result.failure(e)
            }
        } else {
            Result.failure(IllegalArgumentException("Invalid credential type"))
        }
    }
}
