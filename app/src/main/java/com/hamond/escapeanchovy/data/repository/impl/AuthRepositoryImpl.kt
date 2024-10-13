package com.hamond.escapeanchovy.data.repository.impl

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.hamond.escapeanchovy.domain.model.User
import com.hamond.escapeanchovy.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : AuthRepository {
    override fun googleLogin(
        firebaseCredential: AuthCredential,
        callback: (String?, User?) -> Unit
    ) {
        val authResult = auth.signInWithCredential(firebaseCredential)
        authResult.addOnSuccessListener {
            val user = it.user
            val name = user?.displayName ?: "No Name"
            val email = user?.email ?: "No Email"
            callback(null, User(email, name))
        }.addOnFailureListener {
            callback(it.message, null)
        }
    }
}