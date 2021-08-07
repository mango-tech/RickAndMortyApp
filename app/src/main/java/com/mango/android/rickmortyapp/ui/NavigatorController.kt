package com.mango.android.rickmortyapp.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.mango.android.rickmortyapp.ui.alertError.AlertErrorDialog
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NavigatorController @Inject constructor(@ApplicationContext private val context: Context) {

    fun openDetail(activity: AppCompatActivity, characterId: Int, onResult: () -> Unit) {
//        var resultLauncher =
//            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//                onResult.invoke()
//            }
//        val intent = Intent(activity, SomeActivity::class.java)
//        intent.putExtra(DETAIL_EXTRA_CHAR_ID, characterId)
//        resultLauncher.launch(intent)
    }

    fun openError(activity: AppCompatActivity, onResult: () -> Unit) {
        AlertErrorDialog(activity).show(onResult)
    }

    companion object {
        const val DETAIL_EXTRA_CHAR_ID = "detailActivity:charId"

        const val DETAIL_REQUEST_CODE = 0xFF
    }

}