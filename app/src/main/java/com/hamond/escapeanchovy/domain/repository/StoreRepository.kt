package com.hamond.escapeanchovy.domain.repository

import com.hamond.escapeanchovy.domain.model.User

interface StoreRepository {
    fun saveSocialAccountInfo(user:User?, callback:(String?) -> Unit)
}
