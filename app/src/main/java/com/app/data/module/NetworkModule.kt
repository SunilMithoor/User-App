package com.app.data.module

import android.content.Context
import com.app.BuildConfig
import com.app.data.local.sharedprefs.AppSharedPreferences
import com.app.data.remote.retrofit.interceptors.SupportInterceptor
import com.app.data.remote.retrofit.interceptors.UserAgentInterceptor
import com.app.data.remote.retrofit.api.ApiService
import com.app.data.utils.HttpClient.CLIENT_TIME_OUT
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        Timber.d("provideLoggingInterceptor")
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        supportInterceptor: SupportInterceptor,
        @ApplicationContext context: Context,
        appSharedPreferences: AppSharedPreferences
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
            readTimeout(CLIENT_TIME_OUT, TimeUnit.SECONDS)
            addInterceptor(loggingInterceptor)
            addInterceptor(UserAgentInterceptor(appSharedPreferences, context))
            authenticator(supportInterceptor)
            if (BuildConfig.DEBUG) {
                addInterceptor(ChuckerInterceptor(context))
                addInterceptor(provideLoggingInterceptor())
            }
        }.build()
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().apply {
            add(KotlinJsonAdapterFactory())
        }.build()
    }

    @Provides
    @Singleton
    fun provideContext(): ApplicationContext {
        return ApplicationContext()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        Timber.d("provideRetrofit")
        return Retrofit.Builder().apply {
            baseUrl(BuildConfig.BASE_URL)
            client(client)
            addConverterFactory(MoshiConverterFactory.create(moshi))
        }.build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): ApiService {
        Timber.d("provideService")
        return retrofit.create(ApiService::class.java)
    }


}