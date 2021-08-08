package com.mango.android.rickmortyapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.mango.android.domain.entity.CharacterEntity
import com.mango.android.rickmortyapp.R
import com.mango.android.rickmortyapp.databinding.ActivityDetailBinding
import com.mango.android.rickmortyapp.di.ViewModelFactory
import com.mango.android.rickmortyapp.ui.NavigatorController
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: NavigatorController

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel
    private var currentCharacter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        processExtras()
        bindViewModel()

        viewModel.loadCharacter(currentCharacter)
    }

    private fun processExtras() {
        intent.extras?.let { bundle ->
            currentCharacter = bundle.getInt(NavigatorController.DETAIL_EXTRA_CHAR_ID)
        }
    }

    private fun bindViewModel() {
        viewModel =
            ViewModelProviders.of(this, viewModelFactory)[DetailViewModel::class.java]

        viewModel.state().observe(this) { state ->
            when (state) {
                DetailViewModel.State.NotInit, null,
                DetailViewModel.State.Loading -> binding.progressBar.visibility = View.VISIBLE
                DetailViewModel.State.Loaded -> binding.progressBar.visibility = View.GONE
                DetailViewModel.State.Error -> showError()
            }
        }

        viewModel.character().observe(this) {
            populateCharacter(it)
        }
    }

    private fun showError() {
        navigator.openError(this) {
            finish()
        }
    }

    private fun populateCharacter(entity: CharacterEntity) {
        Picasso.get().load(entity.image).error(R.drawable.ic_no_image_24)
            .into(binding.imageViewAvatar)
    }
}