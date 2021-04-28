package com.shishkindenis.childmodule.di

import com.shishkindenis.childmodule.navigator.SendLocationNavigation
import com.shishkindenis.loginmodule.navigation.LoginNavigation
import dagger.Binds
import dagger.Module

@Module
interface LoginModule {

    @Binds
    fun bindLoginNavigation(sendLocationNavigation: SendLocationNavigation): LoginNavigation
}