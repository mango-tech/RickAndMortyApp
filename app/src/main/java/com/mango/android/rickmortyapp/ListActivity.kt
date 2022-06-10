package com.mango.android.rickmortyapp

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mango.android.rickmortyapp.DetailActivity.Companion.start
import com.mango.android.rickmortyapp.ListActivity.CharacterAdapter.CharacterViewHolder
import com.mango.android.rickmortyapp.databinding.ActivityListBinding
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import java.util.concurrent.ExecutionException

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private var mCharacterAdapter: CharacterAdapter = CharacterAdapter(object : OnCharacterClickListener {
        override fun onCharacterClicked(character: Character?) {
            start(this@ListActivity, character!!.id)
        }
    })
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // init recycler view
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = mCharacterAdapter
    }

    override fun onResume() {
        super.onResume()
        try {
            val characters: List<Character?>? = GetCharactersTask().execute().get()
            mCharacterAdapter.bindData(characters)
        } catch (e: ExecutionException) {
            e.printStackTrace()
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss()
        } catch (e: InterruptedException) {
            e.printStackTrace()
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .add(ServerErrorDialogFragment.newInstance(), null)
                .commitAllowingStateLoss()
        }
    }

    // --------------------------------------------------------------------------------------------
    // RecyclerView adapter
    // --------------------------------------------------------------------------------------------
    interface OnCharacterClickListener {
        fun onCharacterClicked(character: Character?)
    }

    class CharacterAdapter(private val mListener: OnCharacterClickListener) :
        RecyclerView.Adapter<CharacterViewHolder>() {
        private var mCharacterList: List<Character?> = ArrayList(0)

        inner class CharacterViewHolder(view: View) : ViewHolder(view) {
            var mName: TextView

            init {
                mName = view.findViewById(R.id.tv_name)
            }
        }

        fun bindData(characters: List<Character?>?) {
            mCharacterList = ArrayList(characters)
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_character, parent, false)
            return CharacterViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
            holder.mName.text = mCharacterList.get(position)!!.name
            holder.itemView.setOnClickListener {
                mListener.onCharacterClicked(
                    mCharacterList[position]
                )
            }
        }

        override fun getItemCount(): Int {
            return mCharacterList.size
        }
    }

    inner class GetCharactersTask() : AsyncTask<Void?, Void?, List<Character>?>() {
         override fun doInBackground(vararg voids: Void?): List<Character>? {
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
                            val list: MutableList<Character> = ArrayList()
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

        private fun parseCharacterJson(string: String): Character? {
            try {
                val `object` = JSONObject(string)
                val c = Character()
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
}