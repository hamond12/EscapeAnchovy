package com.hamond.escapeanchovy.domain.usecase

import androidx.credentials.GetCredentialResponse
import com.google.firebase.auth.AuthCredential
import com.hamond.escapeanchovy.domain.repository.AuthRepository
import javax.inject.Inject

class GoogleLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        firebaseCredential: AuthCredential,
        callback: (Boolean, String?) -> Unit
    ){
        authRepository.googleLogin(firebaseCredential, callback)
    }
}