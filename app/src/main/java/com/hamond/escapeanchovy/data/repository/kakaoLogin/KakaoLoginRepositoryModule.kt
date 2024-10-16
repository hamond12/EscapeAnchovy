package com.hamond.escapeanchovy.data.repository.kakaoLogin

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class KakaoLoginRepositoryModule {
    @Binds
    abstract fun bindKakaoLoginRepositoryModule(kakaoLoginRepository: KakaoLoginRepositoryImpl): KakaoLoginRepository
}