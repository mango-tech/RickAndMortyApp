package com.mango.android.rickmortyapp.ui

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import com.mango.android.rickmortyapp.ui.alertError.AlertErrorDialog
import com.mango.android.rickmortyapp.ui.detail.DetailActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import androidx.core.util.Pair
import java.util.*
import kotlin.collections.ArrayList

class NavigatorController @Inject constructor(@ApplicationContext private val context: Context) {

    fun openDetail(
        activity: AppCompatActivity,
        viewRelation: Pair<View, String>,
        characterId: Int,
        onResult: () -> Unit
    ) {
        val resultLauncher =
            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                onResult.invoke()
            }

        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(activity, viewRelation)
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DETAIL_EXTRA_CHAR_ID, characterId)
        resultLauncher.launch(intent, options)
    }

    fun openError(activity: AppCompatActivity, onResult: () -> Unit) {
        AlertErrorDialog(activity).show(onResult)
    }

    companion object {
        const val DETAIL_EXTRA_CHAR_ID = "detailActivity:charId"

        const val DETAIL_REQUEST_CODE = 0xFF
    }

}