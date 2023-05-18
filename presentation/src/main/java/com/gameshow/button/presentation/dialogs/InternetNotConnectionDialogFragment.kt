package com.gameshow.button.presentation.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.gameshow.button.presentation.R

class InternetNotConnectionDialogFragment() : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity, R.style.MyDialogStyle)
            .setMessage(getString(R.string.internet_connection_false))
            .setCancelable(false)
            .create()
    }
}