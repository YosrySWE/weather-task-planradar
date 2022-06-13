package com.planradar.weatherapptask.util.extensions

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AlertDialog
import com.planradar.weatherapptask.R


fun Context.openAlert(
    title: String = "",
    message: String = "",
    isCancelable: Boolean = false,
    hasPositiveButton: (() -> Unit)? = null,
    hasNegativeButton: (() -> Unit)? = null
) {
    val dialogBuilder = AlertDialog.Builder(this)
    dialogBuilder.setCancelable(isCancelable)


    if (message.isNotEmpty()) {
        dialogBuilder.setMessage(message)
    }

    if (hasPositiveButton != null) {
        dialogBuilder.setPositiveButton(getString((android.R.string.ok))) { dialog, _ ->
            dialog.dismiss()
            hasPositiveButton()
        }
    }

    if (hasNegativeButton != null) {
        dialogBuilder.setNegativeButton(getString((android.R.string.cancel))) { dialog, _ ->
            dialog.dismiss()
            hasNegativeButton()
        }
    }
    val alert = dialogBuilder.create()
    if (title.isNotEmpty()) {
        alert.setTitle(title)
    } else {
        alert.setTitle(getString(R.string.app_name))
    }
    alert.show()
}

val Context.connectivityManager: ConnectivityManager
    get() =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

