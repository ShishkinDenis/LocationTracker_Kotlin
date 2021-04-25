package com.shishkindenis.loginmodule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.shishkindenis.loginmodule.singletons.FirebaseUserSingleton
import com.shishkindenis.loginmodule.SingleLiveEvent


class MainLoginViewModel : ViewModel() {

    //    DELETE - заинжектить
    var firebaseUserSingleton: FirebaseUserSingleton? = FirebaseUserSingleton()

    private var userID: String? = null
    private var user: FirebaseUser? = null

    val module: LiveData<Any>
        get() = moduleLiveData
    private val moduleLiveData = SingleLiveEvent<Any>()

    fun checkIfUserLoggedIn() {
        user = firebaseUserSingleton?.getFirebaseAuth()?.currentUser
        if (user != null) {
//            userID = user!!.uid
//            firebaseUserSingleton?.setUserId(userID)
//            viewState.goToCalendarActivityForResult()

//            сократить код по определению модуля для email,phone,main
            goToSpecificModule()
        }
    }

    fun goToSpecificModule() {
        moduleLiveData.call()
    }


}