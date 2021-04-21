package com.shishkindenis.loginmodule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.shishkindenis.locationtracker_kotlin.singletons.FirebaseUserSingleton
import com.shishkindenis.loginmodule.R
import com.shishkindenis.loginmodule.SingleLiveEvent
import javax.inject.Inject


class PhoneAuthViewModel() : ViewModel() {

    //  TODO DELETE
    private val auth = FirebaseAuth.getInstance()

    lateinit var firebaseUserSingleton: FirebaseUserSingleton
    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var phoneVerificationId: String? = null
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private var userId: String? = null

    //    TODO Можно ли проще? get?
    val toast: LiveData<Int>
        get() = toastLiveData
    private val toastLiveData = SingleLiveEvent<Int>()

    //    TODO naming
    val startCalendarActivity: LiveData<Any>
        get() = startLiveData
    private val startLiveData = SingleLiveEvent<Any>()

    val verifyButton: LiveData<Any>
        get() = verifyButtonLiveData
    private val verifyButtonLiveData = SingleLiveEvent<Any>()

    val phoneNumberError: LiveData<Any>
        get() = phoneNumberErrorLiveData
    private val phoneNumberErrorLiveData = SingleLiveEvent<Any>()

    //    TODO Dagger
    @Inject
    constructor(firebaseUserSingleton: FirebaseUserSingleton) : this() {
        this.firebaseUserSingleton = firebaseUserSingleton
    }

    fun phoneVerificationCallback(): PhoneAuthProvider.OnVerificationStateChangedCallbacks? {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
//                    viewState.showInvalidPhoneNumberError()
                } else if (e is FirebaseTooManyRequestsException) {
                    showToast(R.string.quota_exceeded)
                }
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                phoneVerificationId = verificationId
                forceResendingToken = token
//                TODO naming
                callEnableVerifyButton()
            }
        }
        return callbacks
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
//    firebaseUserSingleton.getFirebaseAuth()?.signInWithCredential(credential)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    //                val user = task.result!!.user
                    //                userId = user!!.uid
                    //                    firebaseUserSingleton!!.setUserId(userId)
                    showToast(R.string.authentication_successful)
                    //                TODO Calendar or SendActivity
                    goToMainActivity()
                } else {
                    showToast(R.string.authentication_failed)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        callShowInvalidPhoneNumberError()
                    }
                }
            }
    }

    fun verifyPhoneNumberWithCode(code: String?) {
        val credential = PhoneAuthProvider.getCredential(phoneVerificationId!!, code!!)
        signInWithPhoneAuthCredential(credential)
    }

    fun showToast(toastMessage: Int) {
        toastLiveData.value = toastMessage
    }

    //    TODO Calendar Activity
    fun goToMainActivity() {
        startLiveData.call()
    }

    //TODO naming
    fun callEnableVerifyButton() {
        verifyButtonLiveData.call()
    }

    fun callShowInvalidPhoneNumberError() {
        phoneNumberErrorLiveData.call()
    }

}