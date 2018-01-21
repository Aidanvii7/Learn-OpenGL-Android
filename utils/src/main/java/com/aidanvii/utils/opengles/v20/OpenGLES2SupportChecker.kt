package com.aidanvii.utils.opengles.v20

import android.app.ActivityManager
import android.content.Context
import android.content.pm.ConfigurationInfo

class OpenGLES2SupportChecker private constructor(private val configurationInfo: ConfigurationInfo) {

    val supportsEs2 get() = configurationInfo.reqGlEsVersion >= 0x20000

    companion object provide : (Context) -> OpenGLES2SupportChecker {
        override fun invoke(context: Context): OpenGLES2SupportChecker =
                (context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).run {
                    OpenGLES2SupportChecker(deviceConfigurationInfo)
                }
    }
}