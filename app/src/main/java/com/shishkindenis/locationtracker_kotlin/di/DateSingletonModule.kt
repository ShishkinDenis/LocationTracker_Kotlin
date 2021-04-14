package com.shishkindenis.locationtracker_kotlin.di

import com.shishkindenis.locationtracker_kotlin.singletons.DateSingleton
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DateSingletonModule {
    @Provides
    @Singleton
    fun provideDateSingleton(): DateSingleton {
        return DateSingleton()
    }
}