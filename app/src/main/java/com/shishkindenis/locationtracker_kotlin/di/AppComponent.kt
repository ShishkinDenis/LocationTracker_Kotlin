package com.shishkindenis.locationtracker_kotlin.di

import com.shishkindenis.locationtracker_kotlin.activities.EmailAuthActivity
import com.shishkindenis.locationtracker_kotlin.activities.MainActivity
import com.shishkindenis.locationtracker_kotlin.activities.PhoneAuthActivity
import com.shishkindenis.locationtracker_kotlin.viewModel.EmailAuthViewModel
import com.shishkindenis.locationtracker_kotlin.viewModel.PhoneAuthViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [DateSingletonModule::class, FirebaseUserSingletonModule::class])
interface AppComponent {
    fun inject(emailAuthActivity: EmailAuthActivity?)

    fun inject(phoneAuthActivity: PhoneAuthActivity?)

    fun inject(mainActivity: MainActivity?)

    //    fun inject(mapActivity: MapActivity?)
//
//    fun inject(calendarActivity: CalendarActivity?)
//
//    fun inject(mainPresenter: MainPresenter?)
//
    fun inject(emailAuthViewModel: EmailAuthViewModel)

    fun inject(phoneAuthViewModel: PhoneAuthViewModel)
//
//    fun inject(calendarPresenter: CalendarPresenter?)
//
//    fun inject(mapPresenter: MapPresenter?)
}