package com.shishkindenis.parentmodule.di

import com.shishkindenis.parentmodule.maps.view.MapsActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
internal abstract class MapActivityModule {
    @ContributesAndroidInjector
    abstract fun provideMapActivity(): MapsActivity
}