package com.mango.android.rickmortyapp.ui.activities.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.lifecycle.lifecycleScope
import com.mango.android.rickmortyapp.databinding.ActivityDetailBinding
import com.mango.android.rickmortyapp.ui.dialogs.ServerErrorDialogFragment
import com.mango.android.rickmortyapp.ui.dialogs.getServerErrorDialog
import com.mango.android.rickmortyapp.ui.viewmodel.details.DetailViewModel
import es.andres.bailen.domain.models.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.ExecutionException
import kotlin.coroutines.coroutineContext

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var mCharacterId = 0
    private val viewModel: DetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setTransitionName(
            binding.imgAvatar,
            TRANSITION_IMG_ID
        )
        ViewCompat.setTransitionName(
            binding.tvNameItem,
            TRANSITION_NAME_ID
        )
        mCharacterId = intent.extras!!.getInt(EXTRA_CHARACTER_ID)
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val response = viewModel.getCharacter(mCharacterId.toString())
                binding.root.post {
                    when (response.status) {
                        DataResult.Status.SUCCESS -> {
                            binding.character = response.data
                        }
                        DataResult.Status.ERROR -> {
                            getServerErrorDialog().show()
                        }
                    }
                }
            } catch (e: ExecutionException) {
                binding.root.post {
                    getServerErrorDialog().show()
                }
            } catch (e: InterruptedException) {
                binding.root.post {
                    getServerErrorDialog().show()
                }
            }
        }

    }


    companion object {
        const val EXTRA_CHARACTER_ID = "EXTRA_CHARACTER_ID"
        const val TRANSITION_IMG_ID = "profileImg"
        const val TRANSITION_NAME_ID = "nameTxt"

        @JvmStatic
        fun start(
            activity: Activity,
            characterId: Int,
            imageView: ImageView,
            nameTxtView: TextView
        ) {
            val intent = Intent(activity, DetailActivity::class.java)
            intent.putExtra(EXTRA_CHARACTER_ID, characterId)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                Pair(imageView, TRANSITION_IMG_ID),
                Pair(nameTxtView, TRANSITION_NAME_ID)
            )

            activity.startActivity(intent, options.toBundle())
        }
    }
}