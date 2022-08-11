package com.app.data.module

import android.content.Context
import com.app.data.local.sharedprefs.AppSharedPreferences
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {
    @Singleton
    @Provides
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
        moshi: Moshi
    ): AppSharedPreferences {
        return AppSharedPreferences(context, moshi)
    }
}