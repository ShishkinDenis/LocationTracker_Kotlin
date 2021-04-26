package com.shishkindenis.loginmodule.activities

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shishkindenis.loginmodule.R

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }

    fun goToSendLocationActivity() {
        var intent: Intent? = null
        try {
            intent = Intent(
                this,
                Class.forName(getString(R.string.send_location_activity))
            )
            finish()
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun goToCalendarActivity() {
        var intent: Intent? = null
        try {
            intent = Intent(
                this,
                Class.forName(getString(R.string.calendar_activity))
            )
            finish()
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun getModuleName(context: Context): String? {
        val packageManager: PackageManager = context.packageManager
        var applicationInfo: ApplicationInfo? = null
        try {
            applicationInfo =
                packageManager.getApplicationInfo(context.applicationInfo.packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return (if (applicationInfo != null) packageManager.getApplicationLabel(applicationInfo) else getString(
            R.string.unknown
        )) as String
    }

    fun checkModuleName(appLabel: String): Boolean {
        if (appLabel == getString(R.string.child_module)) {
            return true
        }
        return false
    }
}