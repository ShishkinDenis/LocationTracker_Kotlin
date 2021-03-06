package com.shishkindenis.loginmodule.auth.phoneAuth.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.shishkindenis.loginmodule.R
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import com.shishkindenis.loginmodule.util.SingleLiveEvent
import javax.inject.Inject

class PhoneAuthViewModel @Inject constructor(var firebaseUserSingleton: FirebaseUserSingleton) : ViewModel() {

    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var phoneVerificationId: String? = null
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    var etPhoneNumber: String = ""
    var etVerificationCode: String = ""

    val toast: LiveData<Int>
        get() = toastLiveData
    private val toastLiveData = SingleLiveEvent<Int>()

    val code: LiveData<Any>
        get() = codeLiveData
    private val codeLiveData = SingleLiveEvent<Any>()

    val applicationModule: LiveData<Any>
        get() = applicationModuleLiveData
    private val applicationModuleLiveData = SingleLiveEvent<Any>()

    val phoneNumberError: LiveData<Any>
        get() = phoneNumberErrorLiveData
    private val phoneNumberErrorLiveData = SingleLiveEvent<Any>()

    val phoneNumber: LiveData<String>
        get() = phoneNumberLiveData
    private val phoneNumberLiveData = SingleLiveEvent<String>()

    val error: LiveData<Any>
        get() = errorLiveData
    private val errorLiveData = SingleLiveEvent<Any>()

    private var _codeSent = MutableLiveData<Boolean>(false)
    var codeSent: LiveData<Boolean> = _codeSent

    private var _progressBarIsShown = MutableLiveData<Boolean>(false)
    var progressBarIsShown: LiveData<Boolean> = _progressBarIsShown


    fun phoneVerificationCallback(): PhoneAuthProvider.OnVerificationStateChangedCallbacks? {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
                    showInvalidPhoneNumberError()
                } else if (e is FirebaseTooManyRequestsException) {
                    showToast(R.string.quota_exceeded)
                }
            }

            override fun onCodeSent(
                    verificationId: String,
                    token: PhoneAuthProvider.ForceResendingToken) {
                phoneVerificationId = verificationId
                forceResendingToken = token
                _codeSent.value = true
            }
        }
        return callbacks
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        firebaseUserSingleton.getFirebaseAuth()?.signInWithCredential(credential)
                ?.addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        showToast(R.string.authentication_successful)
                        goToApplicationModule()
                    } else {
                        showToast(R.string.authentication_failed)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            showInvalidCodeError()
                        }
                    }
                }
    }

    //    TODO
    private fun verifyPhoneNumberWithCode(code: String?) {
        val credential = PhoneAuthProvider.getCredential(phoneVerificationId!!, code!!)
        signInWithPhoneAuthCredential(credential)
    }

    fun showToast(toastMessage: Int) {
        toastLiveData.value = toastMessage
    }

    fun showInvalidPhoneNumberError() {
        phoneNumberErrorLiveData.call()
    }

    private fun goToApplicationModule() {
        applicationModuleLiveData.call()
    }

    private fun showInvalidCodeError() {
        codeLiveData.call()
    }

    private fun showError() {
        errorLiveData.call()
    }

    private fun launchPhoneNumberVerification(phoneNumber: String) {
        phoneNumberLiveData.value = phoneNumber
    }

    fun phoneNumberIsValid(): Boolean {
        return etPhoneNumber.isNotEmpty()
    }

    fun codeIsValid(): Boolean {
        return etVerificationCode.isNotEmpty()
    }

    fun requestCode() {
        _progressBarIsShown.value = true
        if (phoneNumberIsValid()) {
            launchPhoneNumberVerification(etPhoneNumber)
        } else {
            showError()
        }
        _progressBarIsShown.value = false
    }

    fun verifyCode() {
        _progressBarIsShown.value = true
        if (codeIsValid()) {
            verifyPhoneNumberWithCode(etVerificationCode)
        } else {
            showError()
        }
        _progressBarIsShown.value = false
    }

}