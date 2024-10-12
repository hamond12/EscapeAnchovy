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
            /* TODO: User 클래스 만들어서 스토어에 저장 */
            val user = it.user
            val name = user?.displayName
            val email = user?.email
            callback(true, user?.uid)
        }.addOnFailureListener {
            callback(false, it.message)
        }
    }
}