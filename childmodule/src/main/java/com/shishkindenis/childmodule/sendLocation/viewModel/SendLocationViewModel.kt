package com.shishkindenis.childmodule.sendLocation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.shishkindenis.childmodule.R
import com.shishkindenis.childmodule.sendLocation.workers.LocationWorker
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import com.shishkindenis.loginmodule.util.SingleLiveEvent

class SendLocationViewModel : ViewModel() {
    val toast: LiveData<Int>
        get() = toastLiveData
    private val toastLiveData = SingleLiveEvent<Int>()

    val service: LiveData<Int>
        get() = serviceLiveData
    private val serviceLiveData = SingleLiveEvent<Int>()

    fun startLocationWorker() {
        val myWorkRequest =
                OneTimeWorkRequest.Builder(LocationWorker::class.java).build()
        WorkManager.getInstance().enqueue(myWorkRequest)
    }

    fun signOut() {
        stopService()
        FirebaseUserSingleton.getFirebaseAuth()?.signOut()
        showToast(R.string.sign_out_successful)
    }

    fun showToast(toastMessage: Int) {
        toastLiveData.value = toastMessage
    }

    fun stopService() {
        serviceLiveData.call()
    }

}