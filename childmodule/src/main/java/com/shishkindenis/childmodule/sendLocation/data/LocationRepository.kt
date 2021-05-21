package com.shishkindenis.childmodule.sendLocation.data

import com.google.firebase.firestore.FirebaseFirestore
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import javax.inject.Inject


//class LocationRepository @Inject constructor(val firestoreDataBase: FirebaseFirestore?, firebaseUserSingleton: FirebaseUserSingleton?) {
//
////    private val firestoreDataBase = FirebaseFirestore.getInstance()
////    private var userId: String = FirebaseUserSingleton.getFirebaseAuth()?.currentUser!!.uid
//
//    private var userId: String = firebaseUserSingleton?.getFirebaseAuth()?.currentUser!!.uid
//
//    fun addDataToRepository(locationMap: MutableMap<String, Any>) =
//            firestoreDataBase?.collection(userId)?.add(locationMap)
//
//
//}

class LocationRepository @Inject constructor (val firestoreDataBase: FirebaseFirestore?, firebaseUserSingleton: FirebaseUserSingleton?) {
    private var userId: String = firebaseUserSingleton?.getFirebaseAuth()?.currentUser!!.uid

    fun addDataToRepository(locationMap: MutableMap<String, Any>) =
            firestoreDataBase?.collection(userId)?.add(locationMap)
}


//class LocationRepository(val firestoreDataBase: FirebaseFirestore?, firebaseUserSingleton: FirebaseUserSingleton?) {
//    private var userId: String = firebaseUserSingleton?.getFirebaseAuth()?.currentUser!!.uid
//
//    fun addDataToRepository(locationMap: MutableMap<String, Any>) =
//            firestoreDataBase?.collection(userId)?.add(locationMap)
//}