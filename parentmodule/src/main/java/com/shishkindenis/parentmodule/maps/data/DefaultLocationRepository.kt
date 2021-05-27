package com.shishkindenis.parentmodule.maps.data

import com.google.firebase.firestore.FirebaseFirestore
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import com.shishkindenis.parentmodule.singleton.DateSingleton
import javax.inject.Inject


class DefaultLocationRepository @Inject constructor(var firestoreDataBase: FirebaseFirestore,
                                                    var dateSingleton: DateSingleton,
                                                    var firebaseUserSingleton: FirebaseUserSingleton) : LocationRepository {
    private var userId: String? = firebaseUserSingleton.getFirebaseAuth()?.currentUser?.uid
    private val DATE_FIELD = "Date"

    override fun readLocationFromRepository() =
            firestoreDataBase.collection(userId!!).whereEqualTo(DATE_FIELD, dateSingleton.getDate()).get()

}
