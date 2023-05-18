package com.gameshow.button.presentation.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.gameshow.button.presentation.R
import com.gameshow.button.domain.entities.Profile
import com.gameshow.button.presentation.callback.DeleteCallback

class ProfileRemoveDialogFragment(
    private val callback: DeleteCallback,
    private val profile: Profile
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity, R.style.MyDialogStyle)
            .setMessage(getString(R.string.do_you_want_to_delete, profile.nickname))
            .setCancelable(false)
            .setPositiveButton(R.string.yes) { _, _ -> callback.setPositiveButton(profile) }
            .setNegativeButton(R.string.no) { _, _ -> }
            .create()
    }
}