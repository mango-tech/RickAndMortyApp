package com.mango.android.rickmortyapp.ui

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import com.mango.android.rickmortyapp.ui.alertError.AlertErrorDialog
import com.mango.android.rickmortyapp.ui.detail.DetailActivity
import javax.inject.Inject

class NavigatorController @Inject constructor() {

    fun openDetail(
        activity: AppCompatActivity,
        viewRelation: Pair<View, String>,
        characterId: Int
    ) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DETAIL_EXTRA_CHAR_ID, characterId)

        val options =
            ActivityOptionsCompat.makeSceneTransitionAnimation(activity, viewRelation)

        activity.startActivity(intent, options.toBundle())
    }

    fun openError(activity: AppCompatActivity, onResult: () -> Unit) {
        AlertErrorDialog(activity).show(onResult)
    }

    companion object {
        const val DETAIL_EXTRA_CHAR_ID = "detailActivity:charId"

        const val DETAIL_REQUEST_CODE = 0xFF
    }

}