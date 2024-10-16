package com.hamond.escapeanchovy.data.repository.store

import com.hamond.escapeanchovy.data.model.User

interface StoreRepository {
    suspend fun saveAccountInfo(user: User)
}
