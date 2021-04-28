package com.shishkindenis.parentmodule.maps.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import com.shishkindenis.parentmodule.singleton.DateSingleton

class LocationRepository {
    private var firestoreDataBase: FirebaseFirestore? = null
    private var date: String? = null
    private var userId: String? = null
    private val DATE_FIELD = "Date"

    fun readLocationFromRepository(): Task<QuerySnapshot>? {

        firestoreDataBase = FirebaseFirestore.getInstance()
        userId = FirebaseUserSingleton.getFirebaseAuth()?.currentUser?.uid
        date = DateSingleton.getDate()


        return firestoreDataBase?.collection(userId!!)
                ?.whereEqualTo(DATE_FIELD, date)
                ?.get()

    }
}