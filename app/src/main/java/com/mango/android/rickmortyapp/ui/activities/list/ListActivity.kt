package com.mango.android.rickmortyapp.ui.activities.list

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.mango.android.rickmortyapp.databinding.ActivityListBinding
import com.mango.android.rickmortyapp.ui.activities.detail.DetailActivity.Companion.start
import com.mango.android.rickmortyapp.ui.activities.list.adapter.CharacterAdapter
import com.mango.android.rickmortyapp.ui.viewmodel.list.ListViewModel
import es.andres.bailen.domain.models.CharacterModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private var mCharacterAdapter: CharacterAdapter =
        CharacterAdapter(object : CharacterAdapter.OnCharacterClickListener {
            override fun onCharacterClicked(
                character: CharacterModel?,
                imageView: ImageView,
                nameTxtView: TextView
            ) {
                start(this@ListActivity, character!!.id, imageView, nameTxtView)
            }
        })

    private val viewModel: ListViewModel by viewModel()
    private var collectJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // init recycler view
        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = mCharacterAdapter
        lifecycleScope.launch {
            mCharacterAdapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect {
                    binding.recycler.scrollToPosition(0)
                }
        }
    }

    private fun collectData() {
        collectJob?.cancel()
        collectJob = lifecycleScope.launch(Dispatchers.IO) {
            viewModel.characterFlow.collectLatest {
                mCharacterAdapter.submitData(it)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        collectData()
        /*lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = viewModel.getCharacterList()
                binding.root.post {
                    when (response.status) {
                        DataResult.Status.SUCCESS -> {
                            mCharacterAdapter.bindData(response.data)
                        }
                        DataResult.Status.ERROR -> {
                            getServerErrorDialog().show()
                        }
                    }
                }
            } catch (e: ExecutionException) {
                e.printStackTrace()
                binding.root.post { getServerErrorDialog().show() }
            } catch (e: InterruptedException) {
                e.printStackTrace()
                binding.root.post { getServerErrorDialog().show() }
            }
        }*/
    }

}