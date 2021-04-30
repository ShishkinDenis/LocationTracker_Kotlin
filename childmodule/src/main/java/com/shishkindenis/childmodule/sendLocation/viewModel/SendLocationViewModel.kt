package com.shishkindenis.childmodule.sendLocation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.shishkindenis.childmodule.R
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import com.shishkindenis.loginmodule.util.SingleLiveEvent

//TODO inject to ViewModel
class SendLocationViewModel : ViewModel() {
    val toast: LiveData<Int>
        get() = toastLiveData
    private val toastLiveData = SingleLiveEvent<Int>()

    val service: LiveData<Int>
        get() = serviceLiveData
    private val serviceLiveData = SingleLiveEvent<Int>()

    fun signOut() {
        stopService()
        FirebaseUserSingleton.getFirebaseAuth()?.signOut()
        showToast(R.string.sign_out_successful)
    }

    private fun showToast(toastMessage: Int) {
        toastLiveData.value = toastMessage
    }

    private fun stopService() {
        serviceLiveData.call()
    }

}