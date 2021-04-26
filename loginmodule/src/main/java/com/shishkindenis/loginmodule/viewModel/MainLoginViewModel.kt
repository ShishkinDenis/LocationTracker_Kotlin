package com.shishkindenis.loginmodule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.shishkindenis.loginmodule.SingleLiveEvent
import com.shishkindenis.loginmodule.singletons.FirebaseUserSingleton


class MainLoginViewModel : ViewModel() {

    //    DELETE - заинжектить
//    var firebaseUserSingletonO: FirebaseUserSingletonO? = FirebaseUserSingletonO()

    private var userID: String? = null
    private var user: FirebaseUser? = null

    val module: LiveData<Any>
        get() = moduleLiveData
    private val moduleLiveData = SingleLiveEvent<Any>()

    fun checkIfUserLoggedIn() {
//        user = firebaseUserSingletonO?.getFirebaseAuth()?.currentUser
        user = FirebaseUserSingleton.getFirebaseAuth()?.currentUser
        if (user != null) {
//            userID = user!!.uid
//            firebaseUserSingleton?.setUserId(userID)

//            сократить код по определению модуля для email,phone,main
            goToSpecificModule()
        }
    }

    fun goToSpecificModule() {
        moduleLiveData.call()
    }


}