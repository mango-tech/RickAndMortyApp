package com.mango.android.rickmortyapp.ui.dialogs

import android.app.AlertDialog
import android.content.Context
import com.mango.android.rickmortyapp.R

fun Context.getServerErrorDialog(): AlertDialog {
    return AlertDialog.Builder(this).apply {
        setTitle(R.string.server_error_dialog_title)
        setMessage(R.string.server_error_dialog_message)
        setPositiveButton(
            R.string.server_error_dialog_button_caption
        ) { dialog, which -> dialog.dismiss() }
    }.create()
}