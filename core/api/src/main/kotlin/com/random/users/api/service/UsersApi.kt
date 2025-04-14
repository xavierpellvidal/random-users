package com.random.users.api.service

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.random.users.api.model.RandomUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {
    @GET
    fun getUsers(
        @Query("page") page: Int,
        @Query("results") results: Int,
        @Query("seed") seed: String,
    ): Either<CallError, RandomUserResponse>

    companion object {
        const val BASE_URL = "http://api.randomuser.me/"
    }
}
