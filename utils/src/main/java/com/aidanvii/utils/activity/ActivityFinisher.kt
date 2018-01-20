package com.aidanvii.utils.activity

import android.app.Activity
import com.aidanvii.utils.longToastWithMessage

class ActivityFinisher(private val activity: Activity) {

    fun finish() = activity.finish()

    fun finishWithToast(message: String) {
        activity.longToastWithMessage(message).show()
        finish()
    }
}