package com.shishkindenis.childmodule.sendLocation.data

import com.google.firebase.firestore.FirebaseFirestore
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton

class LocationRepository {
    private val firestoreDataBase = FirebaseFirestore.getInstance()
    private var userId: String = FirebaseUserSingleton.getFirebaseAuth()?.currentUser!!.uid

    fun addDataToRepository(locationMap: MutableMap<String, Any>) =
            firestoreDataBase.collection(userId).add(locationMap)

}