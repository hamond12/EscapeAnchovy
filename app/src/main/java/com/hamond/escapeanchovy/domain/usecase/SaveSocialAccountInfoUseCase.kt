package com.hamond.escapeanchovy.domain.usecase

import com.hamond.escapeanchovy.domain.model.User
import com.hamond.escapeanchovy.domain.repository.StoreRepository
import javax.inject.Inject

class SaveSocialAccountInfoUseCase @Inject constructor(
    private val storeRepository: StoreRepository
) {
    operator fun invoke(user: User?, callback: (String?) -> Unit) {
        storeRepository.saveSocialAccountInfo(user, callback)
    }
}