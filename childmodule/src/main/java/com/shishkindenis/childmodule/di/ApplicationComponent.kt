package com.shishkindenis.childmodule.di

import com.shishkindenis.childmodule.sendLocation.services.ForegroundService
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule


@Component(modules = [AndroidSupportInjectionModule::class, ActivitiesModule::class, LocationRepositoryModule::class])
interface ApplicationComponent : AndroidInjector<MyApplication> {

//    TODO Component.Builder
    @Component.Builder

    abstract class Builder : AndroidInjector.Builder<MyApplication>() {

        abstract override fun build(): ApplicationComponent
    }

     fun inject(foregroundService: ForegroundService?)
}

