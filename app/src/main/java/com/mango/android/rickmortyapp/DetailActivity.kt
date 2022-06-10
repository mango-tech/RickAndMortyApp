package com.mango.android.rickmortyapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import android.os.Bundle
import com.mango.android.rickmortyapp.R
import com.mango.android.rickmortyapp.DetailActivity
import com.mango.android.rickmortyapp.DetailActivity.GetCharacterDetailTask
import com.mango.android.rickmortyapp.ServerErrorDialogFragment
import android.os.AsyncTask
import org.json.JSONObject
import android.content.Intent
import org.json.JSONException
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import java.util.concurrent.ExecutionException

class DetailActivity : AppCompatActivity() {
    private var mNameTv: TextView? = null
    private var mStatusTv: TextView? = null
    private var mSpeciesTv: TextView? = null
    private var mTypeTv: TextView? = null
    private var mGenderTv: TextView? = null
    private var mCharacterId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail)
        mNameTv = findViewById(R.id.tv_name_item)
        mStatusTv = findViewById(R.id.tv_status_item)
        mSpeciesTv = findViewById(R.id.tv_species_item)
        mTypeTv = findViewById(R.id.tv_type_item)
        mGenderTv = findViewById(R.id.tv_gender_item)
        mCharacterId = intent.extras!!.getInt(EXTRA_CHARACTER_ID)
    }

    override fun onResume() {
        super.onResume()
        try {
            val (_, name, status, species, type, gender) = GetCharacterDetailTask().execute(
                mCharacterId
            ).get()!!
            mNameTv!!.text = name
            mStatusTv!!.text = status
            mSpeciesTv!!.text = species
            mTypeTv!!.text = type
            mGenderTv!!.text = gender
        } catch (e: ExecutionException) {
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss()
        } catch (e: InterruptedException) {
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss()
        }
    }

    inner class GetCharacterDetailTask : AsyncTask<Int?, Void?, Character?>() {
         override fun doInBackground(vararg integers: Int?): Character? {
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

        private fun parseCharacterJson(string: String): Character? {
            return try {
                val jsonObject = JSONObject(string)
                val c = Character()
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

    companion object {
        const val EXTRA_CHARACTER_ID = "EXTRA_CHARACTER_ID"
        @JvmStatic
        fun start(context: Context, characterId: Int) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_CHARACTER_ID, characterId)
            context.startActivity(intent)
        }
    }
}