package com.app.domain.module

import com.app.domain.manager.OnBoardDataManager
import com.app.domain.repository.OnBoardingFirebaseRepository
import com.app.domain.repository.OnBoardingRepository
import com.app.domain.usecase.DeviceIdUseCase
import com.app.domain.usecase.MessageTokenUseCase
import com.app.domain.usecase.SignInUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
object UseCaseModule {

    /**
     * Returns a [SignInUseCase] instance
     * @param repository [OnBoardingRepository] impl
     * @since 1.0.0
     */
//    @Provides
//    fun provideSignInUseCase(repository: OnBoardingRepository): SignInUseCase =
//        SignInUseCase(
//            repository
//        )

    @Provides
    fun provideSignInUseCase(repository: OnBoardDataManager): SignInUseCase =
        SignInUseCase(
            repository
        )


    /**
     * Returns a [DeviceIdUseCase] instance
     * @param repository [OnBoardingFirebaseRepository] impl
     * @since 1.0.0
     */
    @Provides
    fun provideDeviceIdUseCase(repository: OnBoardingFirebaseRepository): DeviceIdUseCase =
        DeviceIdUseCase(
            repository
        )

    /**
     * Returns a [MessageTokenUseCase] instance
     * @param repository [OnBoardingFirebaseRepository] impl
     * @since 1.0.0
     */
    @Provides
    fun provideMessageTokenUseCase(repository: OnBoardingFirebaseRepository): MessageTokenUseCase =
        MessageTokenUseCase(
            repository
        )
}