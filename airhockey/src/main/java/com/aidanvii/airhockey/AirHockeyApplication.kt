package com.aidanvii.airhockey

import android.app.Application
import com.aidanvii.utils.logger.AndroidLoggerDelegate
import com.aidanvii.utils.logger.CompositeLogger

class AirHockeyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        attachLogger()
    }

    private fun attachLogger() {
        if (BuildConfig.DEBUG) {
            CompositeLogger.attachDelegate(AndroidLoggerDelegate())
        }
    }
}