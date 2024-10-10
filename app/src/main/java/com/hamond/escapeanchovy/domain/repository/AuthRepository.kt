package com.hamond.escapeanchovy.domain.repository

import androidx.credentials.GetCredentialResponse
import com.google.firebase.auth.AuthCredential

// Data Layer
interface AuthRepository {
    suspend fun googleLogin(result: GetCredentialResponse): Result<String>
}
