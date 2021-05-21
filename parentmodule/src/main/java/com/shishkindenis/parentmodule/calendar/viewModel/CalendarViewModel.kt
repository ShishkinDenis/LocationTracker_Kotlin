package com.shishkindenis.parentmodule.calendar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import com.shishkindenis.loginmodule.util.SingleLiveEvent
import com.shishkindenis.parentmodule.R
import javax.inject.Inject

class CalendarViewModel @Inject constructor(var firebaseUserSingleton: FirebaseUserSingleton) : ViewModel() {

    val toast: LiveData<Int>
        get() = toastLiveData
    private val toastLiveData = SingleLiveEvent<Int>()

    fun signOut() {
        firebaseUserSingleton.getFirebaseAuth()?.signOut()
        showToast(R.string.sign_out_successful)
    }

    private fun showToast(toastMessage: Int) {
        toastLiveData.value = toastMessage
    }
}