package com.shishkindenis.parentmodule.maps.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import com.shishkindenis.parentmodule.singleton.DateSingleton
import javax.inject.Inject


interface ILocationRepository {
    fun readLocationFromRepository(): Task<QuerySnapshot>
}

class LocationRepository @Inject constructor(var firestoreDataBase: FirebaseFirestore,
                                             var dateSingleton: DateSingleton,
                                             var firebaseUserSingleton: FirebaseUserSingleton) : ILocationRepository {
    private var userId: String? = firebaseUserSingleton.getFirebaseAuth()?.currentUser?.uid
    private val DATE_FIELD = "Date"

    override fun readLocationFromRepository() =
            firestoreDataBase.collection(userId!!).whereEqualTo(DATE_FIELD, dateSingleton.getDate()).get()

}
