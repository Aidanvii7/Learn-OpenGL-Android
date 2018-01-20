package com.aidanvii.utils

import android.content.Context
import android.widget.Toast


fun Context.longToastWithMessage(message: String): Toast {
    return Toast.makeText(this, message, Toast.LENGTH_LONG)
}