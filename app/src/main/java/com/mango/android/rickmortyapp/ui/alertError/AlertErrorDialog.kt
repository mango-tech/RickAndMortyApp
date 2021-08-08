package com.mango.android.rickmortyapp.ui.alertError

import android.app.Activity
import android.app.AlertDialog
import com.mango.android.rickmortyapp.R

class AlertErrorDialog(private val activity: Activity) {

    private var closeLambda: (() -> Unit)? = null
    private val alertDialog: AlertDialog by lazy {
        AlertDialog.Builder(activity).apply {

            setTitle(R.string.server_error_dialog_title)

            setMessage(R.string.server_error_dialog_message)

            setPositiveButton(
                R.string.server_error_dialog_button_caption
            ) { _, _ -> dismiss() }
        }.create()
    }

    fun show(onClose: () -> Unit) {
        closeLambda = onClose
        alertDialog.show()
    }

    private fun dismiss() {
        alertDialog.dismiss()
        closeLambda?.invoke()
    }
}