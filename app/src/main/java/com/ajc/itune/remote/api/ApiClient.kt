package com.ajc.itune.remote.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    //https://itunes.apple.com/search?term

    @GET("search")
    suspend fun getData(
        @Query("term") term: String
    ): Response<com.ajc.itune.remote.Response>
}