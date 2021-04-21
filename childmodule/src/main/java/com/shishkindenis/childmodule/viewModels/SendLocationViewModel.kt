package com.shishkindenis.childmodule.viewModels

import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.shishkindenis.childmodule.workers.LocationWorker
import com.shishkindenis.locationtracker_kotlin.singletons.FirebaseUserSingleton

class SendLocationViewModel : ViewModel() {
    var firebaseUserSingleton: FirebaseUserSingleton? = null

//    @Inject
//    fun SendLocationPresenter(firebaseUserSingleton: FirebaseUserSingleton?) {
//        this.firebaseUserSingleton = firebaseUserSingleton
//    }

    fun startLocationWorker() {
        val myWorkRequest =
            OneTimeWorkRequest.Builder(LocationWorker::class.java).build()
        WorkManager.getInstance().enqueue(myWorkRequest)
    }

    fun signOut() {
//        viewState.stopService()
        firebaseUserSingleton?.getFirebaseAuth()?.signOut()
//        viewState.showToast(R.string.sign_out_successful)
    }
}