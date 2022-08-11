package com.app.data.remote.retrofit.datasource


import com.app.data.remote.retrofit.api.ApiService
import javax.inject.Inject

class OnBoardingRemoteDataSource
@Inject constructor(private val apiService: ApiService) :
    ApiWrapper() {

    suspend fun fetchSignIn() = getResult { apiService.fetchSignInData() }

}