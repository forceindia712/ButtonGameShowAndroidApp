package com.gameshow.button.presentation.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.gameshow.button.presentation.R
import com.gameshow.button.presentation.callback.BackPressCallback


class GameLobbyBackPressDialogFragment(
    private val closeOrLeave: String,
    private val dialogName: String,
    private val callback: BackPressCallback
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity, R.style.MyDialogStyle)
            .setMessage(getString(R.string.do_you_want_to_exit, closeOrLeave, dialogName))
            .setCancelable(false)
            .setPositiveButton(R.string.yes) { _, _ -> callback.setPositiveButton() }
            .setNegativeButton(R.string.no) { _, _ -> }
            .create()
    }
}