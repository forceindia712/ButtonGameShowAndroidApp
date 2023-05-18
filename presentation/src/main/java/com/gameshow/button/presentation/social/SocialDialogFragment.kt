package com.gameshow.button.presentation.social

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.gameshow.button.presentation.R

class SocialDialogFragment(
    private val callback: SocialCallback,
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
            .setMessage(getString(R.string.do_you_want_to_install_app, "GitHub"))
            .setCancelable(false)
            .setPositiveButton(R.string.yes) { _, _ -> callback.setPositiveButton() }
            .setNegativeButton(R.string.no) { _, _ -> callback.setNegativeButton() }
            .create()
    }
}