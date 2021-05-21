package com.shishkindenis.parentmodule.di

import com.shishkindenis.parentmodule.calendar.view.CalendarActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class CalendarModule {

//        @ContributesAndroidInjector(modules = [InjectionExampleModule::class])
        @ContributesAndroidInjector()
        abstract fun provideCalendarActivity(): CalendarActivity

}