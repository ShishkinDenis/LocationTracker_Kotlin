package com.shishkindenis.parentmodule.maps.data

import com.google.firebase.firestore.FirebaseFirestore
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import com.shishkindenis.parentmodule.singleton.DateSingleton

class LocationRepository {
    private var firestoreDataBase: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var date: String? = DateSingleton.getDate()
    private var userId: String? = FirebaseUserSingleton.getFirebaseAuth()?.currentUser?.uid
    private val DATE_FIELD = "Date"

    fun readLocationFromRepository() =
            firestoreDataBase.collection(userId!!).whereEqualTo(DATE_FIELD, date).get()

}