package com.ar50645.quickreceipe

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment


class WarningDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?)
            : Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.warning)
        builder.setMessage(R.string.warning_message)
        builder.setPositiveButton(R.string.ok, null)
        return builder.create()
    }
}
