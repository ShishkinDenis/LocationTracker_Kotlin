package com.shishkindenis.parentmodule.di

import com.shishkindenis.loginmodule.LoginNavigation
import com.shishkindenis.parentmodule.navigators.CalendarNavigation
import dagger.Binds
import dagger.Module

@Module
interface LoginModule {

    @Binds
    fun bindLoginNavigation(calendarNavigation: CalendarNavigation): LoginNavigation
}