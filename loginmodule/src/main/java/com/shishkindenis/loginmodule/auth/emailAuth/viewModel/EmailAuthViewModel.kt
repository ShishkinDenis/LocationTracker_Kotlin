package com.shishkindenis.loginmodule.auth.emailAuth.viewModel


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.shishkindenis.loginmodule.R
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import com.shishkindenis.loginmodule.util.SingleLiveEvent
import javax.inject.Inject

class EmailAuthViewModel @Inject constructor(var firebaseUserSingleton: FirebaseUserSingleton) : ViewModel() {

    var etEmail: String = ""
    var etPassword: String = ""

    val toast: LiveData<Int>
        get() = toastLiveData
    private val toastLiveData = SingleLiveEvent<Int>()

    val toastWithEmail: LiveData<String>
        get() = toastWithEmailLiveData
    private val toastWithEmailLiveData = SingleLiveEvent<String>()

    val applicationModule: LiveData<Any>
        get() = applicationModuleLiveData
    private val applicationModuleLiveData = SingleLiveEvent<Any>()

    val error: LiveData<Any>
        get() = errorLiveData
    private val errorLiveData = SingleLiveEvent<Any>()

    private var _progressBarIsShown = MutableLiveData<Boolean>(false)
    var progressBarIsShown: LiveData<Boolean> = _progressBarIsShown

    private fun createAccount(email: String, password: String?) {
        firebaseUserSingleton.getFirebaseAuth()?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task: Task<AuthResult?> ->
                    if (task.isSuccessful) {
                        showToastWithEmail("User with email: $email was signed up ")
                    } else {
                        showToast(R.string.signing_up_failed)
                    }
                }
    }

    private fun signIn(email: String, password: String) {
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

    private fun showError() {
        errorLiveData.call()
    }

    fun emailIsValid(): Boolean {
        return etEmail.isNotEmpty()
    }

    fun passwordIsValid(): Boolean {
        return etPassword.isNotEmpty()
    }

    fun registerIfValid() {
        _progressBarIsShown.value = true
        if (emailIsValid() and passwordIsValid()) {
            createAccount(etEmail, etPassword)
        } else {
            showError()
        }
        _progressBarIsShown.value = false
    }

    fun logInIfValid() {
        Log.d("Email", "Email")
        _progressBarIsShown.value = true
        if (emailIsValid() and passwordIsValid()) {
            signIn(etEmail, etPassword)
        } else {
            showError()
        }
        _progressBarIsShown.value = false
    }

}