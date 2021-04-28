package com.shishkindenis.parentmodule.calendar.navigator

import android.app.Activity
import android.content.Intent
import com.shishkindenis.loginmodule.navigation.LoginNavigation
import com.shishkindenis.parentmodule.calendar.view.CalendarActivity
import com.shishkindenis.parentmodule.di.ActivityScope
import javax.inject.Inject


@ActivityScope
class CalendarNavigation @Inject constructor() : LoginNavigation {
    override fun finishLogin(activity: Activity) : Intent {
        return CalendarActivity.getIntent(activity)
    }
}