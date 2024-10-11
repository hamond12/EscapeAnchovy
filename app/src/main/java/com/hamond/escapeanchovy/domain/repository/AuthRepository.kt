package com.hamond.escapeanchovy.domain.repository

import com.google.firebase.auth.AuthCredential

interface AuthRepository {
    fun googleLogin(firebaseCredential: AuthCredential, callback:(Boolean, String?) -> Unit)
}
