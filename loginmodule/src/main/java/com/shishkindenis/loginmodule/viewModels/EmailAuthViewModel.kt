package com.shishkindenis.loginmodule.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.shishkindenis.loginmodule.R
import com.shishkindenis.loginmodule.SingleLiveEvent
import com.shishkindenis.loginmodule.singletons.FirebaseUserSingleton


class EmailAuthViewModel() : ViewModel() {

    val toast: LiveData<Int>
        get() = toastLiveData
    private val toastLiveData = SingleLiveEvent<Int>()

    val toastwithEmail: LiveData<String>
        get() = toastWithEmailLiveData
    private val toastWithEmailLiveData = SingleLiveEvent<String>()

    val module: LiveData<Any>
        get() = moduleLiveData
    private val moduleLiveData = SingleLiveEvent<Any>()

    fun createAccount(email: String, password: String?) {
        FirebaseUserSingleton.getFirebaseAuth()?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    showToastWithEmail("User with email: $email was signed up ")
                } else {
                    showToast(R.string.signing_up_failed)
                }
            }
    }

    fun signIn(email: String, password: String) {
        FirebaseUserSingleton.getFirebaseAuth()?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener { task: Task<AuthResult?> ->
                if (task.isSuccessful) {
                    showToast(R.string.authentication_successful)
                    goToSpecificModule()
                } else {
                    showToast(R.string.authentication_failed)
                }
            }
    }

    fun showToast(toastMessage: Int) {
        toastLiveData.value = toastMessage
    }

    fun showToastWithEmail(toastMessage: String) {
        toastWithEmailLiveData.value = toastMessage
    }

    fun goToSpecificModule() {
        moduleLiveData.call()
    }


}