package com.shishkindenis.locationtracker_kotlin.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.shishkindenis.locationtracker_kotlin.singletons.FirebaseUserSingleton
import javax.inject.Inject

class EmailAuthViewModel @Inject constructor(var firebaseUserSingleton: FirebaseUserSingleton?): ViewModel() {
//class EmailAuthViewModel : ViewModel() {
    private var userId: String? = null


    fun createAccount(email: String, password: String?) {
    firebaseUserSingleton?.getFirebaseAuth()?.createUserWithEmailAndPassword(email, password!!)
        ?.addOnCompleteListener { task: Task<AuthResult?> ->
            if (task.isSuccessful) {
//                    viewState.showToastWithEmail("User with email: $email was signed up ")
            } else {
//                    viewState.showToast(R.string.signing_up_failed)
            }
        }
    }

    fun signIn(email: String?, password: String?) {
    firebaseUserSingleton?.getFirebaseAuth()?.signInWithEmailAndPassword(email!!, password!!)
        ?.addOnCompleteListener { task: Task<AuthResult?> ->
            if (task.isSuccessful) {
                val user = firebaseUserSingleton!!.getFirebaseAuth()?.currentUser
                userId = user!!.uid
//                    firebaseUserSingleton!!.setUserId(userId)
//                    viewState.showToast(R.string.authentication_successful)
//                    viewState.goToCalendarActivity()
            } else {
//                    viewState.showToast(R.string.authentication_failed)
            }
        }
    }

}