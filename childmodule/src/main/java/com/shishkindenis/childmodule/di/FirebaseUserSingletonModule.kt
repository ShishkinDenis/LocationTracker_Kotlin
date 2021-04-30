package com.shishkindenis.childmodule.di

import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseUserSingletonModule {
    @Provides
    @Singleton
    fun provideFirebaseUser(): FirebaseUserSingleton {
        return FirebaseUserSingleton
    }
}