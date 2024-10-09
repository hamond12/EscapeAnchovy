package com.hamond.escapeanchovy.data.repository.module

import com.hamond.escapeanchovy.data.repository.impl.AuthRepositoryImpl
import com.hamond.escapeanchovy.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {
    @Binds
    abstract fun bindAuthRepositoryModule(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}