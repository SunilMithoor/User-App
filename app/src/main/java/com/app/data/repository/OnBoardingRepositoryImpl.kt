package com.app.data.repository

import com.app.data.local.sharedprefs.datasource.OnBoardingLocalDataSource
import com.app.data.remote.firebase.datasource.OnBoardFirebaseAuthRemoteDataSource
import com.app.data.remote.firebase.datasource.OnBoardFirebaseDeviceIdRemoteDataSource
import com.app.data.remote.firebase.datasource.OnBoardFirebaseMessageRemoteDataSource
import com.app.data.remote.retrofit.datasource.OnBoardingRemoteDataSource
import com.app.domain.entity.response.SignInData
import com.app.domain.entity.response.FireBaseAuthUser
import com.app.domain.entity.response.FireBaseDeviceId
import com.app.domain.entity.response.FireBaseMessageToken
import com.app.domain.model.FirebaseCallResponse
import com.app.domain.model.IOTaskResult
import com.app.domain.repository.OnBoardingFirebaseRepository
import com.app.domain.repository.OnBoardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor(
    private val onBoardRemoteDataSource: OnBoardingRemoteDataSource,
    private val onBoardLocalDataSource: OnBoardingLocalDataSource,
    private val onBoardFirebaseDeviceIdRemoteDataSource: OnBoardFirebaseDeviceIdRemoteDataSource,
    private val onBoardFirebaseMessageRemoteDataSource: OnBoardFirebaseMessageRemoteDataSource,
    private val onBoardFirebaseAuthRemoteDataSource: OnBoardFirebaseAuthRemoteDataSource
) :
    OnBoardingRepository, OnBoardingFirebaseRepository {

    override suspend fun fetchIsUserFirstTimeLaunch(): Flow<Boolean> =
        onBoardLocalDataSource.getIsUserFirstTimeLaunch()

    override suspend fun setUserFirstTimeLaunch(value: Boolean): Flow<Boolean> =
        onBoardLocalDataSource.setUserFirstTimeLaunch(value)

    override suspend fun fetchSignIn(): Flow<IOTaskResult<SignInData>> =
        onBoardRemoteDataSource.fetchSignIn()


    override suspend fun fetchDeviceId(): FirebaseCallResponse<FireBaseDeviceId>? =
        onBoardFirebaseDeviceIdRemoteDataSource.fetchDeviceId()

    override suspend fun fetchMessageToken(): FirebaseCallResponse<FireBaseMessageToken>? =
        onBoardFirebaseMessageRemoteDataSource.fetchMessageToken()

    override suspend fun fetchUser(): FirebaseCallResponse<FireBaseAuthUser>? =
        onBoardFirebaseAuthRemoteDataSource.fetchUser()
}