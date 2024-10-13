package com.hamond.escapeanchovy.domain.usecase

import com.google.firebase.auth.AuthCredential
import com.hamond.escapeanchovy.domain.model.User
import com.hamond.escapeanchovy.domain.repository.AuthRepository
import javax.inject.Inject

class GoogleLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke(
        firebaseCredential: AuthCredential,
        callback: (String?, User?) -> Unit
    ) {
        authRepository.googleLogin(firebaseCredential, callback)
    }
}