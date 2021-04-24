package com.shishkindenis.childmodule.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.shishkindenis.childmodule.R
import com.shishkindenis.childmodule.workers.LocationWorker
import com.shishkindenis.locationtracker_kotlin.singletons.FirebaseUserSingleton
import com.shishkindenis.loginmodule.SingleLiveEvent

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
//        viewState.stopService()
        stopService()
        firebaseUserSingleton?.getFirebaseAuth()?.signOut()
//        viewState.showToast(R.string.sign_out_successful)
        showToast(R.string.sign_out_successful)
    }

    fun showToast(toastMessage: Int) {
        toastLiveData.value = toastMessage
    }

    fun stopService(){
    serviceLiveData.call()
    }




}