package com.shishkindenis.parentmodule.di

import com.shishkindenis.loginmodule.navigation.LoginNavigation
import com.shishkindenis.parentmodule.calendar.navigator.CalendarNavigation
import dagger.Binds
import dagger.Module

@Module
interface LoginModule {

    @Binds
    fun bindLoginNavigation(calendarNavigation: CalendarNavigation): LoginNavigation
}