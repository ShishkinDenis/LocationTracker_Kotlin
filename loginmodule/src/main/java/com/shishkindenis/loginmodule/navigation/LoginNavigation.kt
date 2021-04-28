package com.shishkindenis.loginmodule.navigation

import android.app.Activity
import android.content.Intent

interface LoginNavigation {
    fun finishLogin(activity: Activity) : Intent
}