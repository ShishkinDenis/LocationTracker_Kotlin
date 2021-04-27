package com.shishkindenis.childmodule.di

import com.shishkindenis.loginmodule.emailAuth.view.EmailAuthActivity
import com.shishkindenis.loginmodule.view.MainLoginActivity
import com.shishkindenis.loginmodule.view.PhoneAuthActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
interface ActivitiesModule {

    @ContributesAndroidInjector(modules = [LoginModule::class])
    @ActivityScope
    fun provideEmailAuthActivity(): EmailAuthActivity

    @ContributesAndroidInjector(modules = [LoginModule::class])
    @ActivityScope
    fun provideMainLoginActivity(): MainLoginActivity

    @ContributesAndroidInjector(modules = [LoginModule::class])
    @ActivityScope
    fun providePhoneAuthActivity(): PhoneAuthActivity


}

