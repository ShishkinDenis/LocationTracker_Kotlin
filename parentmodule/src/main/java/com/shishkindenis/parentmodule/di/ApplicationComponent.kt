package com.shishkindenis.parentmodule.di

import com.shishkindenis.parentmodule.maps.viewModel.MapsViewModel
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule


//@Component(modules = [AndroidSupportInjectionModule::class, ActivitiesModule::class, CalendarModule::class])
@Component(modules = [AndroidSupportInjectionModule::class, ActivitiesModule::class, CalendarModule::class, MapActivityModule::class])
//@Component(modules = [AndroidSupportInjectionModule::class, ActivitiesModule::class, CalendarModule::class,RepositoryModule::class])
interface ApplicationComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MyApplication>() {

        abstract override fun build(): ApplicationComponent
    }

    fun inject(mapsViewModel: MapsViewModel)

}

