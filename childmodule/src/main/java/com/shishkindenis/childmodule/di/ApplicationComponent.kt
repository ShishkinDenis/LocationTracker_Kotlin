package com.shishkindenis.childmodule.di

import com.shishkindenis.childmodule.sendLocation.services.ForegroundService
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule


//@Component(modules = [AndroidSupportInjectionModule::class, ActivitiesModule::class])
@Component(modules = [AndroidSupportInjectionModule::class, ActivitiesModule::class,ForegroundServiceModule::class, RepositoryModule::class, SendLocationModule::class])
interface ApplicationComponent : AndroidInjector<MyApplication> {

    @Component.Builder

    abstract class Builder : AndroidInjector.Builder<MyApplication>() {

        abstract override fun build(): ApplicationComponent
    }

     fun inject(foregroundService: ForegroundService?)
}

