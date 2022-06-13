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
    override fun getCharacters(): DataResult<List<CharacterModel>> {
        return GetCharactersTask().execute().get()?.let {
            DataResult.success(it)
        } ?: run {
            DataResult.error(errorType = DataResult.ErrorType.NETWORK)
        }
    }


    inner class GetCharactersTask() : AsyncTask<Void?, Void?, List<CharacterModel>?>() {
        override fun doInBackground(vararg voids: Void?): List<CharacterModel>? {
            var url: URL? = null
            try {
                url = URL("https://rickandmortyapi.com/api/character/")
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            var urlConnection: HttpURLConnection? = null
            try {
                urlConnection = url?.openConnection() as HttpURLConnection?
                try {
                    val inputStream = urlConnection?.inputStream
                    val scanner = Scanner(inputStream)
                    scanner.useDelimiter("\\A")
                    val hasInput = scanner.hasNext()
                    if (hasInput) {
                        val json = scanner.next()
                        try {
                            val list: MutableList<CharacterModel> = ArrayList()
                            val `object` = JSONObject(json)
                            if (`object`.has("results")) {
                                val results = `object`.optString("results")
                                val jsonArray = JSONArray(results)
                                for (i in 0 until jsonArray.length()) {
                                    val parsedCharacter = parseCharacterJson(jsonArray.getString(i))
                                    if (parsedCharacter != null) list.add(parsedCharacter)
                                }
                                return list
                            }
                            return list
                        } catch (e: JSONException) {
                            e.printStackTrace()
                            return null
                        }
                    } else {
                        return null
                    }
                } finally {
                    urlConnection?.disconnect()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return null
        }

        private fun parseCharacterJson(string: String): CharacterModel? {
            try {
                val `object` = JSONObject(string)
                val c = CharacterModel()
                if (`object`.has("id")) {
                    c.id = `object`.optInt("id")
                }
                if (`object`.has("name")) {
                    c.name = `object`.optString("name")
                }
                if (`object`.has("status")) {
                    c.status = `object`.optString("status")
                }
                if (`object`.has("species")) {
                    c.species = `object`.optString("species")
                }
                if (`object`.has("type")) {
                    c.type = `object`.optString("type")
                }
                if (`object`.has("gender")) {
                    c.gender = `object`.optString("gender")
                }
                if (`object`.has("image")) {
                    c.image = `object`.optString("image")
                }
                return c
            } catch (e: JSONException) {
                e.printStackTrace()
                return null
            }
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