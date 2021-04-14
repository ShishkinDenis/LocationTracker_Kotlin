package com.shishkindenis.locationtracker_kotlin.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser

class MainViewModel : ViewModel() {

//    Move to ViewModel

    private var userID: String? = null
    private var user: FirebaseUser? = null

//    fun checkIfUserLoggedIn() {
//        user = firebaseUserSingleton?.getFirebaseAuth()?.currentUser
//        if (user != null) {
//            userID = user!!.uid
//            firebaseUserSingleton?.setUserId(userID)
////            viewState.goToCalendarActivityForResult()
////           goToCalendarActivityForResult()
//        }
//    }


//            DELETE
    fun logSomething(){
        Log.d("abc","1");
    }
}