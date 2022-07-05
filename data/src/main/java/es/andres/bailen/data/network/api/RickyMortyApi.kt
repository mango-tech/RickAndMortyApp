package es.andres.bailen.data.network.api

import es.andres.bailen.data.network.model.CharacterDataModel
import es.andres.bailen.data.network.model.CharacterListDataModel
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickyMortyApi {

    @GET("character/")
    suspend fun getCharactersList(@Query("page") page: Int): CharacterListDataModel

    @GET("character/{characterId}")
    suspend fun getCharacter(@Path(value = "characterId") characterId: String): CharacterDataModel
}

fun provideRickyMortyApi(retrofit: Retrofit): RickyMortyApi = retrofit.create(RickyMortyApi::class.java)