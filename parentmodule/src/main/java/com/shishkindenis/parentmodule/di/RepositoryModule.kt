package com.shishkindenis.parentmodule.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {
    var firestoreDataBase: FirebaseFirestore = FirebaseFirestore.getInstance()
    @Provides
    fun provideFirebase(): FirebaseFirestore{
        return  firestoreDataBase
    }
}