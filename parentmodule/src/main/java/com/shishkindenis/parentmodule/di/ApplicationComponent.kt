package com.shishkindenis.parentmodule.di

import com.shishkindenis.parentmodule.calendar.view.CalendarActivity
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule



@Component(modules = [AndroidSupportInjectionModule::class, ActivitiesModule::class,  InjectionExampleModule::class])
interface ApplicationComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApplication>() {

        abstract override fun build(): ApplicationComponent
    }

    fun inject (calendarActivity: CalendarActivity)
}

