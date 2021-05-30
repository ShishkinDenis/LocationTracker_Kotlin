package com.shishkindenis.loginmodule.mainLogin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import com.shishkindenis.loginmodule.util.SingleLiveEvent
import javax.inject.Inject

class MainLoginViewModel @Inject constructor(var firebaseUserSingleton: FirebaseUserSingleton) : ViewModel() {

    val applicationModule: LiveData<Any>
        get() = applicationModuleLiveData
    private val applicationModuleLiveData = SingleLiveEvent<Any>()

    fun checkIfUserLoggedIn() {
        firebaseUserSingleton.getFirebaseAuth()?.currentUser?.let {
            goToApplicationModule()
        }
    }

    private fun goToApplicationModule() {
        applicationModuleLiveData.call()
    }

}