package com.example.ft_hangouts.presentation.dialog

import android.app.Activity
import android.app.Dialog
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.example.ft_hangouts.R

class DialogCreator {
    private var currentDialog: Dialog? = null

    fun showDeleteContactDialog(
        activity: Activity,
        onPositiveClick: (String) -> Unit,
        contactId: String
    ) {
        showDialog(
            activity,
            R.string.delete_contact,
            onPositiveClick,
            contactId,
            R.string.delete,
            R.string.cancel,
            R.string.should_delete_the_contact
        )
    }

    private fun showDialog(
        activity: Activity,
        @StringRes titleResId: Int,
        onPositiveClick: (String) -> Unit,
        contactId: String,
        @StringRes positiveButtonResId: Int,
        @StringRes negativeButtonResId: Int,
        @StringRes messageResId: Int,
    ) {
        currentDialog = AlertDialog.Builder(activity)
            .setCancelable(true)
            .setTitle(titleResId)
            .setMessage(messageResId)
            .setPositiveButton(positiveButtonResId) { _, _ ->
                onPositiveClick(contactId)
            }
            .setNegativeButton(negativeButtonResId) { _, _ -> }
            .show()
    }

//    private fun showDialog(activity: Activity, callbackParams: String) {
//        currentDialog = activity.let {
//            val builderDialog = AlertDialog.Builder(it)
//                .setCancelable(true)
//            title?.let { title ->
//                builderDialog?.setTitle(title)
//            }
//            messageResId?.let { message ->
//                builderDialog?.setMessage(message)
//            }
//            positiveButtonResId?.let { buttonTitle ->
//                builderDialog?.setPositiveButton(buttonTitle) { _, _ ->
//                    onPositiveClick(callbackParams)
//                }
//            }
//            negativeButtonResId?.let { negativeButton ->
//                builderDialog?.setNegativeButton(negativeButton) { _, _ -> }
//            }
//            builderDialog?.show()
//        }
//    }

    fun onDestroy() =
        currentDialog?.dismiss()
}