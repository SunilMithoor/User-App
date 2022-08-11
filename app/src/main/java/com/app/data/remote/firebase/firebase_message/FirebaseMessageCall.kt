package com.app.data.remote.firebase.firebase_message

import com.app.domain.entity.response.FireBaseMessageToken
import com.app.domain.model.FirebaseCallResponse
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.tasks.await

open class FirebaseMessageCall {

    private var firebaseMessageCall: FirebaseMessaging? = null

    init {
        //Initialising auth and updates loginStatusLiveData value to be true if the user is already logged in
        if (firebaseMessageCall == null) {
            firebaseMessageCall = FirebaseMessaging.getInstance()
        }
    }


    suspend fun getMessageToken(): FirebaseCallResponse<FireBaseMessageToken>? {
        return try {
            val token = firebaseMessageCall?.token?.await()
            FirebaseCallResponse.Success(FireBaseMessageToken(token))
        } catch (e: Exception) {
            e.printStackTrace()
            FirebaseCallResponse.Failure(e)
        }
    }

}