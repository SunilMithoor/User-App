package com.app.data.module

import com.app.data.repository.OnBoardingRepositoryImpl
import com.app.domain.repository.OnBoardingFirebaseRepository
import com.app.domain.repository.OnBoardingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providerOnBoardingRepository(repository: OnBoardingRepositoryImpl): OnBoardingRepository {
        return repository
    }

    @Provides
    @Singleton
    fun providerOnBoardingFirebaseRepository(repository: OnBoardingRepositoryImpl): OnBoardingFirebaseRepository {
        return repository
    }


}