package com.app.data.module

import com.app.data.remote.firebase.firebase_auth.FirebaseAuthCall
import com.app.data.remote.firebase.firebase_device_id.FirebaseDeviceIdCall
import com.app.data.remote.firebase.firebase_message.FirebaseMessageCall
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Singleton
    @Provides
    fun provideFirebaseDeviceId(): FirebaseDeviceIdCall {
        return FirebaseDeviceIdCall()
    }

    @Singleton
    @Provides
    fun provideFirebaseMessage(): FirebaseMessageCall {
        return FirebaseMessageCall()
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuthCall {
        return FirebaseAuthCall()
    }
}
