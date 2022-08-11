package com.app.data.remote.firebase.firebase_device_id

import com.app.domain.entity.response.FireBaseDeviceId
import com.app.domain.model.FirebaseCallResponse
import com.google.firebase.installations.FirebaseInstallations
import kotlinx.coroutines.tasks.await

open class FirebaseDeviceIdCall {

    private var firebaseInstallations: FirebaseInstallations? = null


    init {
        if (firebaseInstallations == null) {
            firebaseInstallations = FirebaseInstallations.getInstance()
        }
    }


    suspend fun getDeviceId(): FirebaseCallResponse<FireBaseDeviceId>? {
        return try {
            val deviceId = firebaseInstallations?.getToken(false)?.await()
            FirebaseCallResponse.Success(FireBaseDeviceId(deviceId?.token))
        } catch (e: Exception) {
            e.printStackTrace()
            FirebaseCallResponse.Failure(e)
        }
    }

}