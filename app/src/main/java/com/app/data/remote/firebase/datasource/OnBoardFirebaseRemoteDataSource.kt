package com.app.data.remote.firebase.datasource


import com.app.data.remote.firebase.firebase_auth.FirebaseAuthCall
import com.app.data.remote.firebase.firebase_device_id.FirebaseDeviceIdCall
import com.app.data.remote.firebase.firebase_message.FirebaseMessageCall
import javax.inject.Inject

class OnBoardFirebaseDeviceIdRemoteDataSource
@Inject constructor() : FirebaseDeviceIdCall() {

    suspend fun fetchDeviceId() = getDeviceId()

}

class OnBoardFirebaseMessageRemoteDataSource
@Inject constructor() : FirebaseMessageCall() {

    suspend fun fetchMessageToken() = getMessageToken()

}

class OnBoardFirebaseAuthRemoteDataSource
@Inject constructor() : FirebaseAuthCall() {

    suspend fun fetchUser() = getUser()

}