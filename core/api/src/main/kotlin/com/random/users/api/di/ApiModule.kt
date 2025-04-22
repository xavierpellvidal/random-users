package com.random.users.api.di

import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import com.random.users.api.api.UsersApi
import com.random.users.api.api.UsersApi.Companion.TIMEOUT_SECONDS
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(UsersApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(EitherCallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideUsersApi(retrofit: Retrofit): UsersApi = retrofit.create(UsersApi::class.java)

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(Level.BODY)
        return OkHttpClient
            .Builder()
            .addInterceptor(logging)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }
}
