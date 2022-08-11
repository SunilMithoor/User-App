package com.app.domain.entity.response

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.installations.InstallationTokenResult

data class FireBaseAuthUser(
    val uid: String?,
    val providerId: String?,
    val displayName: String?,
    val email: String?,
    val photoUrl: Uri?,
    val phoneNumber: String?,
    val isEmailVerified: Boolean?,
)


data class FireBaseMessageToken(
    val token: String?,
)


data class FireBaseDeviceId(
    val deviceId: String?,
)


data class FireBaseMessage(
    val signOut: Boolean?,
)


data class FireBaseDatabase(
    val data: String?,
)
