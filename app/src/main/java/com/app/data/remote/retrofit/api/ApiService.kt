package com.app.data.remote.retrofit.api

import com.app.domain.entity.response.SignInData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/fact")
    suspend fun fetchSignInData(): Response<SignInData>


}