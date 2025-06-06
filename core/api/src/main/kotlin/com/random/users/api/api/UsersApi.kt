package com.random.users.api.api

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.random.users.api.model.RandomUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {
    @GET("/")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("results") results: Int,
        @Query("seed") seed: String? = null,
    ): Either<CallError, RandomUserResponse>

    companion object {
        const val TIMEOUT_SECONDS = 30L
        const val BASE_URL = "https://api.randomuser.me/"
    }
}
