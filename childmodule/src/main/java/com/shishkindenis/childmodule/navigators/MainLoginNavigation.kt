package com.shishkindenis.childmodule.navigators

import android.app.Activity
import com.shishkindenis.childmodule.di.ActivityScope
import com.shishkindenis.childmodule.view.SendLocationActivity
import com.shishkindenis.loginmodule.LoginNavigation
import javax.inject.Inject


@ActivityScope
class SendLocationNavigation @Inject constructor(): LoginNavigation {
    override fun finishLogin(activity: Activity) {
        activity.startActivity(SendLocationActivity.getIntent(activity))
//TODO finish()
    }
}