package com.hamond.escapeanchovy.domain.repository

import androidx.credentials.GetCredentialResponse

interface AuthRepository {
    suspend fun googleLogin(result: GetCredentialResponse): Result<String>
}
