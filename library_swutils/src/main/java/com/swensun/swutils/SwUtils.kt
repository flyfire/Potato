package com.swensun.swutils

import android.app.Application
import android.content.pm.ApplicationInfo
import com.blankj.utilcode.util.ActivityUtils
import com.swensun.swutils.shareprefence.SharePreferencesManager
import kotlin.properties.Delegates


object SwUtils {
    fun init(application: Application) {
        SwUtils.application = application
        debug = (application.applicationInfo.flags
                and ApplicationInfo.FLAG_DEBUGGABLE) != 0
        SharePreferencesManager.init(application)

    }

    var debug: Boolean by Delegates.notNull()
        private set
    lateinit var application: Application
        private set
}