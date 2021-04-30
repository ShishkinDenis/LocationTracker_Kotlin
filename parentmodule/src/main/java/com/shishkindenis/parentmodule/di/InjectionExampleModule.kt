package com.shishkindenis.parentmodule.di

import com.shishkindenis.parentmodule.InjectionExample
import dagger.Module
import dagger.Provides

@Module
class InjectionExampleModule(var injectionExample: InjectionExample) {

    @Provides
    fun provideInjectionExample():InjectionExample {
        return injectionExample
    }
}