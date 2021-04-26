package com.shishkindenis.loginmodule.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.shishkindenis.loginmodule.SingleLiveEvent
import com.shishkindenis.loginmodule.singletons.FirebaseUserSingleton


class MainLoginViewModel : ViewModel() {

    private var user: FirebaseUser? = null

    val module: LiveData<Any>
        get() = moduleLiveData
    private val moduleLiveData = SingleLiveEvent<Any>()

    fun checkIfUserLoggedIn() {
        user = FirebaseUserSingleton.getFirebaseAuth()?.currentUser
        if (user != null) {
            goToSpecificModule()
        }
    }

    fun goToSpecificModule() {
        moduleLiveData.call()
    }


}