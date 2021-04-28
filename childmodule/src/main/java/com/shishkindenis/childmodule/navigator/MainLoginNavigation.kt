package com.shishkindenis.childmodule.navigator

import android.app.Activity
import com.shishkindenis.childmodule.di.ActivityScope
import com.shishkindenis.childmodule.sendLocation.view.SendLocationActivity
import com.shishkindenis.loginmodule.navigation.LoginNavigation
import javax.inject.Inject


@ActivityScope
class SendLocationNavigation @Inject constructor() : LoginNavigation {
    override fun finishLogin(activity: Activity) {
        activity.startActivity(SendLocationActivity.getIntent(activity))
    }
}