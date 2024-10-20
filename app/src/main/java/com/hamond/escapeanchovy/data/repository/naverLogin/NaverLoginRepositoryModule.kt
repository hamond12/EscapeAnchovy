package com.hamond.escapeanchovy.data.repository.naverLogin

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NaverLoginRepositoryModule {
    @Binds
    abstract fun bindNaverLoginRepositoryModule(naverLoginRepository: NaverLoginRepositoryImpl): NaverLoginRepository
}