package com.hamond.escapeanchovy.data.repository.googleLogin

import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.firebase.auth.AuthCredential
import com.hamond.escapeanchovy.data.model.User

interface GoogleLoginRepository {
    fun createCredentialRequest(): GetCredentialRequest
    fun checkCredentialType(result: GetCredentialResponse): AuthCredential
    suspend fun signInWithCredential(firebaseCredential: AuthCredential): User
}
