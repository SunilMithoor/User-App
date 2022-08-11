package com.app.domain.usecase


import com.app.domain.entity.response.FireBaseDeviceId
import com.app.domain.entity.response.FireBaseMessageToken
import com.app.domain.entity.response.SignInData
import com.app.domain.manager.OnBoardDataManager
import com.app.domain.model.FirebaseCallResponse
import com.app.domain.model.IOTaskResult
import com.app.domain.repository.OnBoardingFirebaseRepository
import com.app.domain.repository.OnBoardingRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class IsUserFirstTimeLaunchUseCase @Inject constructor(private val onBoardingRepo: OnBoardingRepository) :
    IUseCase3<None, Flow<Boolean>> {

    @ExperimentalCoroutinesApi
    override suspend fun execute(input: None): Flow<Boolean> =
        onBoardingRepo.fetchIsUserFirstTimeLaunch()
}

@Singleton
class SetUserFirstTimeLaunchUseCase @Inject constructor(private val onBoardingRepo: OnBoardingRepository) :
    IUseCase3<Boolean, Flow<Boolean>> {

    @ExperimentalCoroutinesApi
    override suspend fun execute(input: Boolean): Flow<Boolean> =
        onBoardingRepo.setUserFirstTimeLaunch(input)
}

@Singleton
class SaveUserDataUseCase @Inject constructor(private val onBoardingRepo: OnBoardingRepository) :
    IUseCase3<Boolean, Flow<Boolean>> {

    @ExperimentalCoroutinesApi
    override suspend fun execute(input: Boolean): Flow<Boolean> =
        onBoardingRepo.setUserFirstTimeLaunch(input)
}

//@Singleton
//class SignInUseCase @Inject constructor(private val onBoardingRepo: OnBoardingRepository) :
//    IUseCase<None, SignInData> {
//
//    @ExperimentalCoroutinesApi
//    override suspend fun execute(input: None): Flow<IOTaskResult<SignInData>> =
//        onBoardingRepo.fetchSignIn()
//}

@Singleton
class SignInUseCase @Inject constructor(private val onBoardDataManager: OnBoardDataManager) :
    IUseCase<None, SignInData> {

    @ExperimentalCoroutinesApi
    override suspend fun execute(input: None): Flow<IOTaskResult<SignInData>> =
        onBoardDataManager.fetchSignIn()
}

@Singleton
class DeviceIdUseCase @Inject constructor(private val onBoardingFirebaseRepository: OnBoardingFirebaseRepository) :
    IUseCase2<None, FireBaseDeviceId> {

    @ExperimentalCoroutinesApi
    override suspend fun execute(input: None): FirebaseCallResponse<FireBaseDeviceId>? =
        onBoardingFirebaseRepository.fetchDeviceId()
}

@Singleton
class MessageTokenUseCase @Inject constructor(private val onBoardingFirebaseRepository: OnBoardingFirebaseRepository) :
    IUseCase2<None, FireBaseMessageToken> {

    @ExperimentalCoroutinesApi
    override suspend fun execute(input: None): FirebaseCallResponse<FireBaseMessageToken>? =
        onBoardingFirebaseRepository.fetchMessageToken()
}