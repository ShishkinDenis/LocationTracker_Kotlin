package com.shishkindenis.childmodule.sendLocation.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton

class LocationRepository {
    private val firestoreDataBase = FirebaseFirestore.getInstance()
    private var userId: String = FirebaseUserSingleton.getFirebaseAuth()?.currentUser!!.uid

    fun addDataToRepository(locationMap: MutableMap<String, Any>): Task<DocumentReference> {
        return firestoreDataBase.collection(userId)
                .add(locationMap)
    }
}