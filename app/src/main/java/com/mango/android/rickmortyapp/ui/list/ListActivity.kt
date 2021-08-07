package com.mango.android.rickmortyapp.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.mango.android.rickmortyapp.databinding.ActivityListBinding
import com.mango.android.rickmortyapp.di.ViewModelFactory
import com.mango.android.rickmortyapp.ui.NavigatorController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    lateinit var viewModel: ListViewModel

    @Inject
    lateinit var navigator: NavigatorController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindView()
        bindViewModel()
    }

    private fun bindView(){
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.reset()
        }
    }

    private fun bindViewModel() {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)[ListViewModel::class.java]

        viewModel.state.observe(this, { state ->
            when (state) {
                ListViewModel.State.ErrorLoading -> {
                    navigator.openError(this) {
                        viewModel.reset()
                    }
                }
                ListViewModel.State.Loaded, ListViewModel.State.LoadedEndOfData -> {
                    // TODO Put the data on the adapter and notify
                    binding.swipeRefresh.isRefreshing = false
                }
                ListViewModel.State.Loading -> {
                    binding.swipeRefresh.isRefreshing = true
                }
                ListViewModel.State.NotInit, null -> {
                    viewModel.loadNextPage()
                }
            }
        })

        viewModel.characters.observe(this, { items ->

        })
    }

}