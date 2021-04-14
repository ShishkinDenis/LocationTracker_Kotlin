package com.shishkindenis.locationtracker_kotlin.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.shishkindenis.locationtracker_kotlin.singletons.FirebaseUserSingleton
import javax.inject.Inject

class PhoneAuthViewModel @Inject constructor(var firebaseUserSingleton: FirebaseUserSingleton?) : ViewModel() {
//class PhoneAuthViewModel  : ViewModel() {
    private var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks? = null
    private var phoneVerificationId: String? = null
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken? = null
    private var userId: String? = null

    fun phoneVerificationCallback(): PhoneAuthProvider.OnVerificationStateChangedCallbacks? {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                if (e is FirebaseAuthInvalidCredentialsException) {
//                    viewState.showInvalidPhoneNumberError()
                } else if (e is FirebaseTooManyRequestsException) {
//                    viewState.showToast(R.string.quota_exceeded)
                }
            }

            override fun onCodeSent(verificationId: String,
                                    token: PhoneAuthProvider.ForceResendingToken
            ) {
                phoneVerificationId = verificationId
                forceResendingToken = token
//                viewState.enableVerifyButton()
            }
        }
        return callbacks
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
    firebaseUserSingleton?.getFirebaseAuth()?.signInWithCredential(credential)
        ?.addOnCompleteListener { task: Task<AuthResult> ->
            if (task.isSuccessful) {
                val user = task.result!!.user
                userId = user!!.uid
//                    firebaseUserSingleton!!.setUserId(userId)
//                    viewState.showToast(R.string.authentication_successful)
//                    viewState.goToCalendarActivity()
            } else {
//                    viewState.showToast(R.string.authentication_failed)
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                        viewState.showInvalidCodeError()
                }
            }
        }
    }

    fun verifyPhoneNumberWithCode(code: String?) {
        val credential = PhoneAuthProvider.getCredential(phoneVerificationId!!, code!!)
        signInWithPhoneAuthCredential(credential)
    }

}