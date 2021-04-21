package com.shishkindenis.loginmodule.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.shishkindenis.loginmodule.SingleLiveEvent


class MainLoginViewModel : ViewModel() {

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
    fun logSomething() {
        Log.d("abc", "1")
    }

    val toast: LiveData<String>
        get() = toastLiveData

    private val toastLiveData = SingleLiveEvent<String>()

    fun doSomething() {
        toastLiveData.value = "Hello world!"
    }

}