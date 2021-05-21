package com.shishkindenis.parentmodule.di

import com.shishkindenis.loginmodule.auth.emailAuth.view.EmailAuthActivity
import com.shishkindenis.loginmodule.auth.phoneAuth.view.PhoneAuthActivity
import com.shishkindenis.loginmodule.mainLogin.view.MainLoginActivity
import com.shishkindenis.parentmodule.calendar.view.CalendarActivity
import com.shishkindenis.parentmodule.maps.view.MapsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface ActivitiesModule {

    @ContributesAndroidInjector(modules = [LoginModule::class])
    @ActivityScope
    fun provideEmailAuthActivity(): EmailAuthActivity

    @ContributesAndroidInjector(modules = [LoginModule::class])
    @ActivityScope
    fun provideMainLoginActivity(): MainLoginActivity

    @ContributesAndroidInjector(modules = [LoginModule::class])
    @ActivityScope
    fun providePhoneAuthActivity(): PhoneAuthActivity

    @ContributesAndroidInjector()
    fun provideCalendarActivity(): CalendarActivity

    @ContributesAndroidInjector
    fun provideMapActivity(): MapsActivity

}

