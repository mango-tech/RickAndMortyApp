package com.mango.android.rickmortyapp.ui.activities.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.mango.android.rickmortyapp.databinding.ActivityListBinding
import com.mango.android.rickmortyapp.ui.activities.detail.DetailActivity.Companion.start
import com.mango.android.rickmortyapp.ui.dialogs.ServerErrorDialogFragment
import com.mango.android.rickmortyapp.ui.viewmodel.list.ListViewModel
import es.andres.bailen.domain.models.CharacterModel
import java.util.*
import java.util.concurrent.ExecutionException
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private var mCharacterAdapter: CharacterAdapter =
        CharacterAdapter(object : CharacterAdapter.OnCharacterClickListener {
            override fun onCharacterClicked(character: CharacterModel?) {
                start(this@ListActivity, character!!.id)
            }
        })

    private val viewModel: ListViewModel by viewModel()

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
            val characters: List<CharacterModel?> = viewModel.getCharacterList()
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


}