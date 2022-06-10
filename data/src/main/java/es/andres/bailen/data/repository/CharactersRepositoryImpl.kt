package es.andres.bailen.data.repository

import android.os.AsyncTask
import es.andres.bailen.domain.models.CharacterModel
import es.andres.bailen.domain.repository.CharactersRepository
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class CharactersRepositoryImpl: CharactersRepository {
    override fun getCharacters(): List<CharacterModel> {
        return GetCharactersTask().execute().get() ?: listOf()
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

    override fun getCharacterDetail(characterId: String): CharacterModel? {
        return GetCharacterDetailTask().execute(characterId).get()
    }


    inner class GetCharacterDetailTask : AsyncTask<String?, Void?, CharacterModel?>() {
        override fun doInBackground(vararg integers: String?): CharacterModel? {
            var url: URL? = null
            try {
                url = URL("https://rickandmortyapi.com/api/character/" + integers[0])
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            var urlConnection: HttpURLConnection? = null
            try {
                urlConnection = url?.openConnection() as HttpURLConnection?
                return try {
                    val inputStream = urlConnection?.inputStream
                    val scanner = Scanner(inputStream)
                    scanner.useDelimiter("\\A")
                    val hasInput = scanner.hasNext()
                    if (hasInput) {
                        val json = scanner.next()
                        parseCharacterJson(json)
                    } else {
                        null
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
            return try {
                val jsonObject = JSONObject(string)
                val c = CharacterModel()
                if (jsonObject.has("id")) {
                    c.id = jsonObject.optInt("id")
                }
                if (jsonObject.has("name")) {
                    c.name = jsonObject.optString("name")
                }
                if (jsonObject.has("status")) {
                    c.status = jsonObject.optString("status")
                }
                if (jsonObject.has("species")) {
                    c.species = jsonObject.optString("species")
                }
                if (jsonObject.has("type")) {
                    c.type = jsonObject.optString("type")
                }
                if (jsonObject.has("gender")) {
                    c.gender = jsonObject.optString("gender")
                }
                if (jsonObject.has("image")) {
                    c.image = jsonObject.optString("image")
                }
                c
            } catch (e: JSONException) {
                e.printStackTrace()
                null
            }
        }
    }
}