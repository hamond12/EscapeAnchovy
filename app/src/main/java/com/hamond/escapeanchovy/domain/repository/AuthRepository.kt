package com.hamond.escapeanchovy.domain.repository

import com.google.firebase.auth.AuthCredential
import com.hamond.escapeanchovy.domain.model.User

interface AuthRepository {
    fun googleLogin(firebaseCredential: AuthCredential, callback:(String?, User?) -> Unit)
}
