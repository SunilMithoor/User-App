package com.app.data.local.sharedprefs.datasource

import com.app.data.local.sharedprefs.AppSharedPreferences
import com.app.data.utils.AppConstants.USER
import com.app.data.utils.AppConstants.USER_DATA
import com.app.data.utils.Constants.FIRST_TIME_LAUNCH
import com.app.domain.entity.response.SignInData
import com.app.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class OnBoardingLocalDataSource @Inject constructor(private val appSharedPreferences: AppSharedPreferences) {

//    fun getUser(): User? {
//        return appSharedPreferences.getValueModel(USER)
//    }


    fun saveUser(user: User?) {
        user?.let {
            appSharedPreferences.saveModel(USER, it)
        }
    }

    fun saveUserData(user: SignInData?) {
        user?.let {
            appSharedPreferences.saveModel(USER_DATA, it)
        }
    }


    fun saveSession(user: User?) {
        user?.let {
            appSharedPreferences.saveModel(USER, it)
        }
    }

    suspend fun getIsUserFirstTimeLaunch(): Flow<Boolean> {
        return flow {
            val launch = appSharedPreferences.get(FIRST_TIME_LAUNCH, Boolean::class.java)
            emit(launch)
            return@flow
        }.catch {
            emit(false)
            return@catch
        }.flowOn(Dispatchers.IO)
    }

    suspend fun setUserFirstTimeLaunch(value: Boolean): Flow<Boolean> {
        return flow {
            appSharedPreferences.put(FIRST_TIME_LAUNCH, value)
            emit(true)
            return@flow
        }.catch {
            emit(false)
            return@catch
        }.flowOn(Dispatchers.IO)
    }


    fun saveIsFirstTimeAppLaunch(value: Boolean) {
        appSharedPreferences.put(FIRST_TIME_LAUNCH, value)
    }

    fun logOut() {
        appSharedPreferences.clearAll()
    }

}