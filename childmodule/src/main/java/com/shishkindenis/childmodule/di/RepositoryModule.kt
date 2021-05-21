package com.shishkindenis.childmodule.di

import com.google.firebase.firestore.FirebaseFirestore
import com.shishkindenis.childmodule.sendLocation.data.ILocationRepository
import com.shishkindenis.childmodule.sendLocation.data.LocationRepository
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import dagger.Module
import dagger.Provides


@Module
class RepositoryModule {
    var firestoreDataBase: FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideFirebase(): FirebaseFirestore {
        return firestoreDataBase
    }

    @Provides
    fun provideFirebaseUserSingleton(): FirebaseUserSingleton {
        return FirebaseUserSingleton
    }

    var iRepository: ILocationRepository = LocationRepository(firestoreDataBase, FirebaseUserSingleton)

    @Provides
    fun provideLocationRepository(): ILocationRepository {
        return iRepository
    }

}