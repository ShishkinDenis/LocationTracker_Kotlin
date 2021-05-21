package com.shishkindenis.loginmodule.mainLogin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import com.shishkindenis.loginmodule.util.SingleLiveEvent
import javax.inject.Inject

class MainLoginViewModel @Inject constructor(var firebaseUserSingleton: FirebaseUserSingleton) : ViewModel() {

    private var user: FirebaseUser? = null

    val applicationModule: LiveData<Any>
        get() = applicationModuleLiveData
    private val applicationModuleLiveData = SingleLiveEvent<Any>()

    fun checkIfUserLoggedIn() {
        user = firebaseUserSingleton.getFirebaseAuth()?.currentUser
        user?.let {
            goToApplicationModule()
        }
    }

    private fun goToApplicationModule() {
        applicationModuleLiveData.call()
    }


}