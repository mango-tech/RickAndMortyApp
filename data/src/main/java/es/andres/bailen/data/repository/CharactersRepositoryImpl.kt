package es.andres.bailen.data.repository

import android.os.AsyncTask
import es.andres.bailen.data.mappers.CharacterMapper
import es.andres.bailen.data.network.api.RickyMortyApi
import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.models.DataResult
import es.andres.bailen.domain.repository.CharactersRepository
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class CharactersRepositoryImpl(private val rickyMortyApi: RickyMortyApi) : CharactersRepository {

    override suspend fun getCharacters(): DataResult<List<CharacterModel>> {
        return try {
            DataResult.success(rickyMortyApi.getCharactersList(0).results.map {
                CharacterMapper().mapCharacterDataToModel(
                    it
                )
            })
        } catch (e: Exception) {
            e.printStackTrace()
            DataResult.error()
        }
    }

    override suspend fun getCharacterDetail(characterId: String): DataResult<CharacterModel> {
        return try {
            DataResult.success(
                CharacterMapper().mapCharacterDataToModel(
                    rickyMortyApi.getCharacter(
                        characterId
                    )
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            DataResult.error()
        }
    }
}