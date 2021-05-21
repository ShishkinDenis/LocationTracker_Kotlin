package com.shishkindenis.childmodule.sendLocation.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import javax.inject.Inject

interface ILocationRepository {
    fun addDataToRepository(locationMap: MutableMap<String, Any>): Task<DocumentReference>?
}

class LocationRepository @Inject constructor(val firestoreDataBase: FirebaseFirestore?, firebaseUserSingleton: FirebaseUserSingleton?) : ILocationRepository {
    private var userId: String = firebaseUserSingleton?.getFirebaseAuth()?.currentUser!!.uid

    override fun addDataToRepository(locationMap: MutableMap<String, Any>) =
            firestoreDataBase?.collection(userId)?.add(locationMap)
}
