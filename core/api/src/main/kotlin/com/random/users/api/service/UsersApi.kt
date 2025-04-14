package com.random.users.api.service

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {
    @GET
    fun getUsers(
        @Query("page") page: Int,
        @Query("results") results: Int = PAGE_SIZE,
        @Query("seed") seed: String,
    ): Either<CallError, Unit>

    companion object {
        const val PAGE_SIZE = 10
        const val BASE_URL = "http://api.randomuser.me/"
    }
}
