package com.hamond.escapeanchovy.data.repository.googleLogin

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GoogleLoginRepositoryModule {
    @Binds
    abstract fun bindGoogleLoginRepositoryModule(googleLoginRepository: GoogleLoginRepositoryImpl): GoogleLoginRepository
}