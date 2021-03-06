package com.shishkindenis.parentmodule.di

import com.google.firebase.firestore.FirebaseFirestore
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import com.shishkindenis.parentmodule.maps.data.DefaultLocationRepository
import com.shishkindenis.parentmodule.maps.data.LocationRepository
import com.shishkindenis.parentmodule.singleton.DateSingleton
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
    fun provideDateSingleton(): DateSingleton {
        return DateSingleton
    }

    @Provides
    fun provideFirebaseUserSingleton(): FirebaseUserSingleton {
        return FirebaseUserSingleton
    }

    var repository: LocationRepository = DefaultLocationRepository(firestoreDataBase, DateSingleton, FirebaseUserSingleton)

    @Provides
    fun provideLocationRepository(): LocationRepository {
        return repository
    }

}