package com.hamond.escapeanchovy.data.repository.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.hamond.escapeanchovy.constants.Collection.USER
import com.hamond.escapeanchovy.domain.model.User
import com.hamond.escapeanchovy.domain.repository.StoreRepository
import javax.inject.Inject

class StoreRepositoryImpl @Inject constructor(
    private val store: FirebaseFirestore
) : StoreRepository {
    override fun saveSocialAccountInfo(user: User?, callback: (String?) -> Unit) {
        store.collection(USER).document(user!!.email).set(user).addOnSuccessListener {
            callback(null)
        }.addOnFailureListener {
            callback(it.message)
        }
    }
}