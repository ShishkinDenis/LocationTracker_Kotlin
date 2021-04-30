package com.shishkindenis.childmodule.di

import com.google.firebase.firestore.FirebaseFirestore
import com.shishkindenis.childmodule.sendLocation.data.LocationRepository
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import dagger.Module
import dagger.Provides

@Module
class LocationRepositoryModule() {
    private var firestoreDataBase : FirebaseFirestore? = FirebaseFirestore.getInstance()
    private var firebaseUserSingleton : FirebaseUserSingleton = FirebaseUserSingleton
    private val repository = LocationRepository(firestoreDataBase,firebaseUserSingleton)

    @Provides
    fun provideLocationRepository(): LocationRepository {
        return repository
    }
}

