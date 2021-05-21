package com.shishkindenis.loginmodule.auth.emailAuth.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.shishkindenis.loginmodule.R
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import com.shishkindenis.loginmodule.util.SingleLiveEvent
import javax.inject.Inject

class EmailAuthViewModel @Inject constructor(var firebaseUserSingleton: FirebaseUserSingleton) : ViewModel() {

    val toast: LiveData<Int>
        get() = toastLiveData
    private val toastLiveData = SingleLiveEvent<Int>()

    val toastWithEmail: LiveData<String>
        get() = toastWithEmailLiveData
    private val toastWithEmailLiveData = SingleLiveEvent<String>()

    val applicationModule: LiveData<Any>
        get() = applicationModuleLiveData
    private val applicationModuleLiveData = SingleLiveEvent<Any>()

    fun createAccount(email: String, password: String?) {
        firebaseUserSingleton.getFirebaseAuth()?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        showToastWithEmail("User with email: $email was signed up ")
                    } else {
                        showToast(R.string.signing_up_failed)
                    }
                }
    }

    fun signIn(email: String, password: String) {
        firebaseUserSingleton.getFirebaseAuth()?.signInWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        showToast(R.string.authentication_successful)
                        goToApplicationModule()
                    } else {
                        showToast(R.string.authentication_failed)
                    }
                }
    }

    private fun showToast(toastMessage: Int) {
        toastLiveData.value = toastMessage
    }

    private fun showToastWithEmail(toastMessage: String) {
        toastWithEmailLiveData.value = toastMessage
    }

    private fun goToApplicationModule() {
        applicationModuleLiveData.call()
    }


}