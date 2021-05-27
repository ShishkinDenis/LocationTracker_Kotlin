package com.shishkindenis.childmodule.sendLocation.data

import com.google.firebase.firestore.FirebaseFirestore
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import javax.inject.Inject


class DefaultLocationRepository @Inject constructor(val firestoreDataBase: FirebaseFirestore?,
                                                    firebaseUserSingleton: FirebaseUserSingleton?) : LocationRepository {
    private var userId: String? = firebaseUserSingleton?.getFirebaseAuth()?.currentUser?.uid

    override fun addDataToRepository(locationMap: MutableMap<String, Any>) =
//        TODO
//        firestoreDataBase?.collection(userId!!)?.add(locationMap)
        userId?.let { firestoreDataBase?.collection(it)?.add(locationMap) }
}
