package com.shishkindenis.childmodule.di

import com.shishkindenis.childmodule.sendLocation.services.ForegroundService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForegroundServiceModule {
    @ContributesAndroidInjector()
    abstract fun provideForegroundService(): ForegroundService

}