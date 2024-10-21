package com.hamond.escapeanchovy.data.repository.googleLogin

import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.hamond.escapeanchovy.BuildConfig
import com.hamond.escapeanchovy.data.model.User
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class GoogleLoginRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : GoogleLoginRepository {

    override fun createCredentialRequest(): GetCredentialRequest {
        val googleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false) // 디바이스의 모든 Google 계정 선택 가능
            .setAutoSelectEnabled(false) // Google 계정 자동선택 비활성화
            .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
            .build()

        return GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
    }

    override fun checkCredentialType(result: GetCredentialResponse): AuthCredential {
        return when (val credential = result.credential) {
            is CustomCredential -> {
                if (credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    val idToken = googleIdTokenCredential.idToken
                    GoogleAuthProvider.getCredential(idToken, null)
                } else {
                    throw Exception("Unsupported credential type")
                }
            }
            else -> {
                throw Exception("Invalid credential type")
            }
        }
    }

    override suspend fun loginWithCredential(firebaseCredential: AuthCredential): User {
        return try {
            val authResult = auth.signInWithCredential(firebaseCredential).await()
            val email = authResult.user?.email ?: ""
            val name = authResult.user?.displayName ?: ""
            User(email, name)
        } catch (e: Exception) {
            throw Exception(e.message)
        }
    }
}