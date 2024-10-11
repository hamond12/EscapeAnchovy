package com.hamond.escapeanchovy.data.repository.impl

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.hamond.escapeanchovy.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override fun googleLogin(
        firebaseCredential: AuthCredential,
        callback: (Boolean, String?) -> Unit
    ) {
        val authResult = firebaseAuth.signInWithCredential(firebaseCredential)
        authResult.addOnSuccessListener {
            callback(true, it.user?.uid)
        }.addOnFailureListener {
            callback(false, it.message)
        }
    }
}