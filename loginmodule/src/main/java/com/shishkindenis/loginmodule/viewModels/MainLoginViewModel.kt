package com.shishkindenis.loginmodule.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.shishkindenis.loginmodule.singletons.FirebaseUserSingleton
import com.shishkindenis.loginmodule.util.SingleLiveEvent


class MainLoginViewModel : ViewModel() {

    private var user: FirebaseUser? = null

    val applicationModule: LiveData<Any>
        get() = applicationModuleLiveData
    private val applicationModuleLiveData = SingleLiveEvent<Any>()

    fun checkIfUserLoggedIn() {
        user = FirebaseUserSingleton.getFirebaseAuth()?.currentUser
        if (user != null) {
            goToApplicationModule()
        }
    }

    fun goToApplicationModule() {
        applicationModuleLiveData.call()
    }


}