package com.project.listapp.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UsersApi {

    @GET("api/")
    suspend fun getUsers(
        @Query("page") page: Int,
        @Query("results") pageCount: Int,
    ): Response<ApiResponse>

    companion object {
        const val BASE_URL = "https://randomuser.me/"
    }
}

data class ApiResponse(
    val results: List<UsersDto>
)