package com.shishkindenis.childmodule.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.shishkindenis.childmodule.R
import com.shishkindenis.childmodule.workers.LocationWorker
import com.shishkindenis.loginmodule.SingleLiveEvent
import com.shishkindenis.loginmodule.singletons.FirebaseUserSingleton

class SendLocationViewModel : ViewModel() {
//    var firebaseUserSingleton: FirebaseUserSingleton? = null

    //    DELETE
    var firebaseUserSingleton: FirebaseUserSingleton? = FirebaseUserSingleton()

//    @Inject
//    fun SendLocationPresenter(firebaseUserSingleton: FirebaseUserSingleton?) {
//        this.firebaseUserSingleton = firebaseUserSingleton
//    }

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
        firebaseUserSingleton?.getFirebaseAuth()?.signOut()
        showToast(R.string.sign_out_successful)
    }

    fun showToast(toastMessage: Int) {
        toastLiveData.value = toastMessage
    }

    fun stopService() {
        serviceLiveData.call()
    }

}