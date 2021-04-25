package com.shishkindenis.loginmodule.di

import com.shishkindenis.loginmodule.EmailAuthActivity
import com.shishkindenis.loginmodule.MainLoginActivity
import com.shishkindenis.loginmodule.PhoneAuthActivity
import com.shishkindenis.loginmodule.viewModel.EmailAuthViewModel
import com.shishkindenis.loginmodule.viewModel.PhoneAuthViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
//@Component(modules = [DateSingletonModule::class, FirebaseUserSingletonModule::class])
@Component(modules = [FirebaseUserSingletonModule::class])
interface AppComponent {
    fun inject(emailAuthActivity: EmailAuthActivity?)

    fun inject(phoneAuthActivity: PhoneAuthActivity?)

    fun inject(mainActivity: MainLoginActivity?)

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