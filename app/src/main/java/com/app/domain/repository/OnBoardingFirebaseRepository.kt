package com.app.domain.repository


import com.app.domain.entity.response.FireBaseAuthUser
import com.app.domain.entity.response.FireBaseDeviceId
import com.app.domain.entity.response.FireBaseMessageToken
import com.app.domain.model.FirebaseCallResponse


interface OnBoardingFirebaseRepository {

    suspend fun fetchDeviceId(): FirebaseCallResponse<FireBaseDeviceId>?

    suspend fun fetchMessageToken(): FirebaseCallResponse<FireBaseMessageToken>?

    suspend fun fetchUser(): FirebaseCallResponse<FireBaseAuthUser>?


}