package com.app.domain.manager

import com.app.data.local.sharedprefs.datasource.OnBoardingLocalDataSource
import com.app.domain.entity.response.SignInData
import com.app.domain.mapper.saveUserData
import com.app.domain.model.IOTaskResult
import com.app.domain.repository.OnBoardingRepository
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject


class OnBoardDataManager @Inject constructor(
    private val onBoardingRepo: OnBoardingRepository,
    private val onBoardingLocalDataSource: OnBoardingLocalDataSource
) : OnBoardingRepository {

    override suspend fun fetchIsUserFirstTimeLaunch(): Flow<Boolean> =
        onBoardingRepo.fetchIsUserFirstTimeLaunch()

    override suspend fun setUserFirstTimeLaunch(value: Boolean): Flow<Boolean> =
        onBoardingRepo.setUserFirstTimeLaunch(value)

//    override suspend fun fetchSignIn(): Flow<IOTaskResult<SignInData>> =
//        onBoardingRepo.fetchSignIn()

    override suspend fun fetchSignIn(): Flow<IOTaskResult<SignInData>> {
        val data = onBoardingRepo.fetchSignIn()
        data.collect {
            Timber.d("OnBoardDataManager res ::$it")
            saveUserData(it, onBoardingLocalDataSource)
//            when (it) {
//                is IOTaskResult.OnSuccess -> {
//                    val vals = ViewState.RenderSuccess(it.data)
//                    vals.output.fact
//                    vals.output.length
//                }
//                is IOTaskResult.OnFailed -> ViewState.RenderFailure(it.throwable)
//            }
//            onBoardingLocalDataSource.saveUser()
        }
        return data
    }


}