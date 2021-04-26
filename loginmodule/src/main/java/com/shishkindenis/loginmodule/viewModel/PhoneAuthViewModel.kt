package com.shishkindenis.loginmodule.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.shishkindenis.loginmodule.R
import com.shishkindenis.loginmodule.SingleLiveEvent
import com.shishkindenis.loginmodule.singletons.FirebaseUserSingleton


class PhoneAuthViewModel() : ViewModel() {

    //  TODO DELETE
//    private val auth = FirebaseAuth.getInstance()

//    lateinit var firebaseUserSingletonO: FirebaseUserSingletonO
    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var phoneVerificationId: String? = null
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private var userId: String? = null

    //    TODO Можно ли проще? get?
    val toast: LiveData<Int>
        get() = toastLiveData
    private val toastLiveData = SingleLiveEvent<Int>()

    val module: LiveData<Any>
        get() = moduleLiveData
    private val moduleLiveData = SingleLiveEvent<Any>()

    val verifyButton: LiveData<Any>
        get() = verifyButtonLiveData
    private val verifyButtonLiveData = SingleLiveEvent<Any>()

    val phoneNumberError: LiveData<Any>
        get() = phoneNumberErrorLiveData
    private val phoneNumberErrorLiveData = SingleLiveEvent<Any>()

    //    TODO Dagger
//    @Inject
//    constructor(firebaseUserSingleton: FirebaseUserSingleton) : this() {
//        this.firebaseUserSingleton = firebaseUserSingleton
//    }

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
        FirebaseUserSingleton.getFirebaseAuth()?.signInWithCredential(credential)
//        auth.signInWithCredential(credential)
                ?.addOnCompleteListener { task: Task<AuthResult> ->
                    if (task.isSuccessful) {
                        //                val user = task.result!!.user
                        //                userId = user!!.uid
                        //                    firebaseUserSingleton!!.setUserId(userId)
                        showToast(R.string.authentication_successful)
                        //                TODO Calendar or SendActivity
//                        goToMainActivity()
                        goToSpecificModule()
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

    //TODO naming
    fun callEnableVerifyButton() {
        verifyButtonLiveData.call()
    }

    fun callShowInvalidPhoneNumberError() {
        phoneNumberErrorLiveData.call()
    }

    fun goToSpecificModule() {
        moduleLiveData.call()
    }

}