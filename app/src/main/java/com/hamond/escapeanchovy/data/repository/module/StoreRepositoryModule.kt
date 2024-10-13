package com.hamond.escapeanchovy.data.repository.module

import com.hamond.escapeanchovy.data.repository.impl.StoreRepositoryImpl
import com.hamond.escapeanchovy.domain.repository.StoreRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class StoreRepositoryModule {
    @Binds
    abstract fun bindStoreRepositoryModule(storeRepository: StoreRepositoryImpl): StoreRepository
}