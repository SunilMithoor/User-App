package com.app.data.remote.firebase.firebase_auth

import com.app.domain.entity.request.FirebaseRequest
import com.app.domain.entity.response.FireBaseAuthUser
import com.app.domain.entity.response.FireBaseMessage
import com.app.domain.model.FirebaseCallResponse
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

open class FirebaseAuthCall {

    private var auth: FirebaseAuth? = null

    init {
        //Initialising auth and updates loginStatusLiveData value to be true if the user is already logged in
        if (auth == null) {
            auth = FirebaseAuth.getInstance()
        }
    }


    suspend fun signUpWithEmailPassword(firebaseRequest: FirebaseRequest): FirebaseCallResponse<FireBaseAuthUser>? {
        return try {
            auth?.createUserWithEmailAndPassword(
                firebaseRequest.email,
                firebaseRequest.password
            )?.await()
            FirebaseCallResponse.Success(
                FireBaseAuthUser(
                    auth?.currentUser?.uid,
                    auth?.currentUser?.providerId,
                    auth?.currentUser?.displayName,
                    auth?.currentUser?.email,
                    auth?.currentUser?.photoUrl,
                    auth?.currentUser?.phoneNumber,
                    auth?.currentUser?.isEmailVerified
                )
            )
        } catch (exception: Exception) {
            FirebaseCallResponse.Failure(exception)
        }
    }


    suspend fun signInWithEmailPassword(firebaseRequest: FirebaseRequest): FirebaseCallResponse<FireBaseAuthUser>? {
        return try {
            auth?.signInWithEmailAndPassword(
                firebaseRequest.email,
                firebaseRequest.password
            )?.await()
            FirebaseCallResponse.Success(
                FireBaseAuthUser(
                    auth?.currentUser?.uid,
                    auth?.currentUser?.providerId,
                    auth?.currentUser?.displayName,
                    auth?.currentUser?.email,
                    auth?.currentUser?.photoUrl,
                    auth?.currentUser?.phoneNumber,
                    auth?.currentUser?.isEmailVerified
                )
            )
        } catch (exception: Exception) {
            FirebaseCallResponse.Failure(exception)
        }
    }

    suspend fun signOut(): FirebaseCallResponse<FireBaseMessage>? {
        return try {
            auth?.signOut()
            FirebaseCallResponse.Success(FireBaseMessage(true))
        } catch (exception: Exception) {
            FirebaseCallResponse.Failure(exception)
        }
    }


    suspend fun getUser(): FirebaseCallResponse<FireBaseAuthUser>? {
        return try {
            FirebaseCallResponse.Success(
                FireBaseAuthUser(
                    auth?.currentUser?.uid,
                    auth?.currentUser?.providerId,
                    auth?.currentUser?.displayName,
                    auth?.currentUser?.email,
                    auth?.currentUser?.photoUrl,
                    auth?.currentUser?.phoneNumber,
                    auth?.currentUser?.isEmailVerified
                )
            )
        } catch (exception: Exception) {
            FirebaseCallResponse.Failure(exception)
        }
    }

    suspend fun sendPasswordReset(firebaseRequest: FirebaseRequest): FirebaseCallResponse<FireBaseAuthUser>? {
        return try {
            auth?.sendPasswordResetEmail(
                firebaseRequest.email
            )?.await()
            FirebaseCallResponse.Success(
                FireBaseAuthUser(
                    auth?.currentUser?.uid,
                    auth?.currentUser?.providerId,
                    auth?.currentUser?.displayName,
                    auth?.currentUser?.email,
                    auth?.currentUser?.photoUrl,
                    auth?.currentUser?.phoneNumber,
                    auth?.currentUser?.isEmailVerified
                )
            )
        } catch (exception: Exception) {
            FirebaseCallResponse.Failure(exception)
        }
    }

}


