package com.shishkindenis.loginmodule.mainLogin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseUser
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import com.shishkindenis.loginmodule.util.SingleLiveEvent


class MainLoginViewModel : ViewModel() {

    private var user: FirebaseUser? = null

    val applicationModule: LiveData<Any>
        get() = applicationModuleLiveData
    private val applicationModuleLiveData = SingleLiveEvent<Any>()

    fun checkIfUserLoggedIn() {
        user = FirebaseUserSingleton.getFirebaseAuth()?.currentUser
        user?.let {
            goToApplicationModule()
        }
    }

    private fun goToApplicationModule() {
        applicationModuleLiveData.call()
    }


}