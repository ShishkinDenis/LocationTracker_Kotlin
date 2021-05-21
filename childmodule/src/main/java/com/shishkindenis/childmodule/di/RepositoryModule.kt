package com.shishkindenis.childmodule.di

import com.google.firebase.firestore.FirebaseFirestore
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import dagger.Module
import dagger.Provides


@Module
class RepositoryModule {
    var firestoreDataBase: FirebaseFirestore = FirebaseFirestore.getInstance()
    @Provides
    fun provideFirebase(): FirebaseFirestore{
        return  firestoreDataBase
    }

    @Provides
    fun provideFirebaseUserSingleton(): FirebaseUserSingleton {
        return  FirebaseUserSingleton
    }

}