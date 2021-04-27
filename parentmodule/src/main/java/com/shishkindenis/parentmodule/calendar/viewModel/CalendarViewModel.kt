package com.shishkindenis.parentmodule.calendar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shishkindenis.loginmodule.singletons.FirebaseUserSingleton
import com.shishkindenis.loginmodule.util.SingleLiveEvent
import com.shishkindenis.parentmodule.R

class CalendarViewModel : ViewModel() {

    val toast: LiveData<Int>
        get() = toastLiveData
    private val toastLiveData = SingleLiveEvent<Int>()

    fun signOut() {
        FirebaseUserSingleton.getFirebaseAuth()?.signOut()
        showToast(R.string.sign_out_successful)
    }

    fun showToast(toastMessage: Int) {
        toastLiveData.value = toastMessage
    }
}