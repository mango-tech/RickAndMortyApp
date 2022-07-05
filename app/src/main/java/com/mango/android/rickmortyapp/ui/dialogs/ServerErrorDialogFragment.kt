package com.mango.android.rickmortyapp.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import com.mango.android.rickmortyapp.R
import androidx.fragment.app.DialogFragment

class ServerErrorDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialogBuilder = AlertDialog.Builder(activity)
        alertDialogBuilder.setTitle(R.string.server_error_dialog_title)
        alertDialogBuilder.setMessage(R.string.server_error_dialog_message)
        alertDialogBuilder.setPositiveButton(
            R.string.server_error_dialog_button_caption
        ) { dialog, which -> dismiss() }
        return alertDialogBuilder.create()
    }

    companion object {
        fun newInstance(): ServerErrorDialogFragment {
            return ServerErrorDialogFragment()
        }
    }
}