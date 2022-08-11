package com.app.domain.repository


import com.app.domain.entity.response.SignInData
import com.app.domain.model.IOTaskResult
import kotlinx.coroutines.flow.Flow


interface OnBoardingRepository {

    suspend fun fetchIsUserFirstTimeLaunch(): Flow<Boolean>

    suspend fun setUserFirstTimeLaunch(value: Boolean): Flow<Boolean>

    suspend fun fetchSignIn(): Flow<IOTaskResult<SignInData>>

}