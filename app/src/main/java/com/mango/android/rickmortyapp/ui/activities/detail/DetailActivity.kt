package com.mango.android.rickmortyapp.ui.activities.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mango.android.rickmortyapp.databinding.ActivityDetailBinding
import com.mango.android.rickmortyapp.ui.dialogs.ServerErrorDialogFragment
import com.mango.android.rickmortyapp.ui.dialogs.getServerErrorDialog
import com.mango.android.rickmortyapp.ui.viewmodel.details.DetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.ExecutionException

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var mCharacterId = 0
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mCharacterId = intent.extras!!.getInt(EXTRA_CHARACTER_ID)
    }

    override fun onResume() {
        super.onResume()
        try {
            val character = viewModel.getCharacter(mCharacterId.toString())
            character?.let {
                binding.character = it
            }
        } catch (e: ExecutionException) {
            getServerErrorDialog().show()
        } catch (e: InterruptedException) {
            getServerErrorDialog().show()
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