package com.mango.android.data.net.provider

import com.mango.android.data.net.model.CharacterResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitAPI {

    companion object {
        fun create(baseUrl: String): RetrofitAPI {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build().create(RetrofitAPI::class.java)
        }
    }

    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int = 1): CharacterResponse
}