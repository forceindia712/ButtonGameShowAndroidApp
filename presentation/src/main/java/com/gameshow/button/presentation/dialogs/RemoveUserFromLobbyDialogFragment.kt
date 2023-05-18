package com.gameshow.button.presentation.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.gameshow.button.presentation.R
import com.gameshow.button.presentation.callback.DeleteUserFromLobbyCallback

class RemoveUserFromLobbyDialogFragment(
    private val name: String,
    private val callback: DeleteUserFromLobbyCallback
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity, R.style.MyDialogStyle)
            .setMessage(getString(R.string.remove_from_lobby, name))
            .setCancelable(false)
            .setPositiveButton(R.string.yes) { _, _ -> callback.deleteUser() }
            .setNegativeButton(R.string.no) { _, _ -> }
            .create()
    }
}