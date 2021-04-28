package com.shishkindenis.childmodule.navigator

import android.app.Activity
import android.content.Intent
import com.shishkindenis.childmodule.di.ActivityScope
import com.shishkindenis.childmodule.sendLocation.view.SendLocationActivity
import com.shishkindenis.loginmodule.navigation.LoginNavigation
import javax.inject.Inject


@ActivityScope
class SendLocationNavigation @Inject constructor() : LoginNavigation {
   override fun getPostLoginActivity(activity: Activity) : Intent {
       return SendLocationActivity.getIntent(activity)
    }

}