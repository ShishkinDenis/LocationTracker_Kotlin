package com.shishkindenis.parentmodule.calendar.navigator

import android.app.Activity
import com.shishkindenis.loginmodule.navigation.LoginNavigation
import com.shishkindenis.parentmodule.calendar.view.CalendarActivity
import com.shishkindenis.parentmodule.di.ActivityScope
import javax.inject.Inject


@ActivityScope
class CalendarNavigation @Inject constructor() : LoginNavigation {
    override fun finishLogin(activity: Activity) {
        activity.startActivity(CalendarActivity.getIntent(activity))
    }
}