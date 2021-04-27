package com.shishkindenis.childmodule.di

import com.shishkindenis.childmodule.navigators.SendLocationNavigation
import com.shishkindenis.loginmodule.LoginNavigation
import dagger.Binds
import dagger.Module

@Module
interface LoginModule {

    @Binds
    fun bindLoginNavigation(sendLocationNavigation: SendLocationNavigation): LoginNavigation
}