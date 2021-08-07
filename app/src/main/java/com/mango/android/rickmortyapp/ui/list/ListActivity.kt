package com.mango.android.rickmortyapp.ui.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mango.android.domain.entity.CharacterEntity
import com.mango.android.rickmortyapp.databinding.ActivityListBinding
import com.mango.android.rickmortyapp.di.ViewModelFactory
import com.mango.android.rickmortyapp.ui.NavigatorController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: NavigatorController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: ActivityListBinding
    private lateinit var viewModel: ListViewModel
    private lateinit var adapter: CharacterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
        bindViewModel()

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

    private fun bindView() {
        binding.swipeRefresh.setOnRefreshListener { viewModel.reset() }

        adapter = CharacterAdapter()
        adapter.onDisplayLastItem = { viewModel.loadNextPage() }
        adapter.onItemClick = { displayDetail(it.id) }

        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewItems.adapter = adapter
    }

    private fun displayDetail(id: Int) {
        navigator.openDetail(this, id) {
            // Nothing, should refresh?
        }
    }

    private fun bindViewModel() {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)[ListViewModel::class.java]

        viewModel.state.observe(this, { state ->
            when (state) {
                ListViewModel.State.ErrorLoading ->
                    navigator.openError(this) {
                        viewModel.reset()
                    }

                ListViewModel.State.Loaded, ListViewModel.State.LoadedEndOfData ->
                    binding.swipeRefresh.isRefreshing = false

                ListViewModel.State.Loading ->
                    binding.swipeRefresh.isRefreshing = true

                ListViewModel.State.NotInit, null ->
                    viewModel.loadNextPage()

            }
        })
    }

    companion object {
        @JvmStatic
        @BindingAdapter("data")
        fun setRecyclerViewProperties(recyclerView: RecyclerView?, data: List<CharacterEntity>?) {
            val adapter = recyclerView?.adapter
            if (adapter is CharacterAdapter) {
                data?.let {
                    adapter.setData(it)
                }
            }
        }
    }

}

