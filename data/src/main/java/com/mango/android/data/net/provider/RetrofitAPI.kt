package com.mango.android.data.net.provider

import com.mango.android.data.net.model.CharacterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int = 1): CharacterResponse
}