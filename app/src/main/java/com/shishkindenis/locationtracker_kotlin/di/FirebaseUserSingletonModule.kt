package com.shishkindenis.locationtracker_kotlin.di

import com.shishkindenis.locationtracker_kotlin.singletons.FirebaseUserSingleton
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseUserSingletonModule {
    @Provides
    @Singleton
    fun provideFirebaseUser(): FirebaseUserSingleton {
        return FirebaseUserSingleton()
    }
}